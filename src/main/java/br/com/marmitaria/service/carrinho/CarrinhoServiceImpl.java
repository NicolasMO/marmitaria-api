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

        validarRegrasMarmita(produto, ingredientesId);
        Set<Ingrediente> ingredientes = new HashSet<>(ingredienteRepository.findAllById(ingredientesId));

        CarrinhoItem item = new CarrinhoItem(
                carrinho,
                produto,
                dto.quantidade(),
                dto.observacao(),
                ingredientes
        );

        carrinho.getItens().add(item);
        carrinhoRepository.save(carrinho);

        return mapCarrinhoToDTO(carrinho);
    }

    private Carrinho criarCarrinho(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Carrinho carrinho = new Carrinho(usuario, new ArrayList<>());

        return carrinhoRepository.save(carrinho);
    }

    private void validarRegrasMarmita(Produto produto, List<Long> ingredientesId) {
        if (produto.getTipo() == TipoProduto.BEBIDA) {
            if (ingredientesId != null && !ingredientesId.isEmpty()) {
                throw new RuntimeException("Bebidas não podem ter ingredientes.");
            }
            return;
        }

        if (ingredientesId == null || ingredientesId.isEmpty()) {
            throw new RuntimeException("Marmitas devem conter ingredientes");
        }

        List<Ingrediente> ingredientes = ingredienteRepository.findAllById(ingredientesId);

        long proteina = ingredientes.stream().filter(i -> i.getCategoria() == CategoriaIngrediente.PROTEINA).count();
        long carboidrato = ingredientes.stream().filter(i -> i.getCategoria() == CategoriaIngrediente.CARBOIDRATO).count();
        long complemento = ingredientes.stream().filter(i -> i.getCategoria() == CategoriaIngrediente.COMPLEMENTO).count();

        if (produto.getTipo() == TipoProduto.MARMITA_PEQUENA) {
            if (proteina != 1) throw new RuntimeException("Marmita pequena requer exatamente 1 proteína.");
            if (carboidrato < 1 || carboidrato > 2) throw new RuntimeException("Marmita pequena deve ter entre 1 e 2 carboidratos.");
            if (complemento > 2) throw new RuntimeException("Marmita pequena deve ter entre nenhum ou 2 complementos.");
        }

        if (produto.getTipo() == TipoProduto.MARMITA_GRANDE) {
            if (proteina < 1 || proteina > 2) throw new RuntimeException("Marmita grande deve ter entre 1 e 2 proteínas.");
            if (carboidrato < 1 || carboidrato > 3) throw new RuntimeException("Marmita grande deve ter entre 1 e 3 carboidratos.");
            if (complemento > 4) throw new RuntimeException("Marmita grande deve ter entre nenhum ou 4 complementos.");
        }
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
