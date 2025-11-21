package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.dto.carrinho.AdicionarCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.RespostaCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.RespostaCarrinhoItemDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.CarrinhoItem;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.enums.CategoriaIngrediente;
import br.com.marmitaria.enums.TipoProduto;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.ingrediente.IngredienteRepository;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.carrinho.strategy.RegraBebida;
import br.com.marmitaria.service.carrinho.strategy.RegraMarmita;
import br.com.marmitaria.service.carrinho.strategy.RegraProduto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    @Autowired
    CarrinhoRepository carrinhoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    IngredienteRepository ingredienteRepository;

    @Override
    public RespostaCarrinhoDTO listarCarrinho(Long usuarioId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado."));

        return mapCarrinhoToDTO(carrinho);
    }

    @Override
    @Transactional
    public RespostaCarrinhoDTO adicionarItem(Long usuarioId, AdicionarCarrinhoItemDTO dto) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> criarCarrinho(usuarioId));

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        List<Long> ingredientesId = dto.ingredientesId() != null ? dto.ingredientesId() : List.of();

        RegraProduto regra = obterRegra(produto);

        List<Ingrediente> ingredientes = ingredienteRepository.findAllById(ingredientesId);

        regra.validar(produto, ingredientes);

        CarrinhoItem item = new CarrinhoItem(
                carrinho,
                produto,
                dto.quantidade(),
                dto.observacao(),
                new HashSet<>(ingredientes)
        );

        carrinho.getItens().add(item);
        carrinhoRepository.save(carrinho);

        return mapCarrinhoToDTO(carrinho);
    }


    @Override
    @Transactional
    public void removerItem(Long usuarioId, Long itemId) {
        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrinho do usuário não encontrado."));

        CarrinhoItem item = carrinho.getItens().stream().filter(i -> i.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho."));

        carrinho.getItens().remove(item);

        carrinhoRepository.save(carrinho);
    }

    // Metodos Privados

    private Carrinho criarCarrinho(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Carrinho carrinho = new Carrinho(usuario, new ArrayList<>());

        return carrinhoRepository.save(carrinho);
    }

    private RegraProduto obterRegra(Produto produto) {
        return switch (produto.getTipo()) {
            case BEBIDA -> new RegraBebida();
            case MARMITA_PEQUENA, MARMITA_GRANDE -> new RegraMarmita();
            default -> throw new RuntimeException("Tipo de produto não suportado.");
        };
    }

    private RespostaCarrinhoDTO mapCarrinhoToDTO(Carrinho carrinho) {

        List<RespostaCarrinhoItemDTO> itens = carrinho.getItens().stream()
                .map(item -> new RespostaCarrinhoItemDTO(
                        item.getId(),
                        item.getProduto().getNome(),
                        item.getProduto().getPreco_unitario(),
                        item.getQuantidade(),
                        item.getObservacao(),
                        item.getIngredientes().stream()
                                .map(Ingrediente::getNome)
                                .collect(Collectors.toSet())
                ))
                .toList();

        return new RespostaCarrinhoDTO(
                carrinho.getId(),
                carrinho.getUsuario().getId(),
                itens
        );
    }
}
