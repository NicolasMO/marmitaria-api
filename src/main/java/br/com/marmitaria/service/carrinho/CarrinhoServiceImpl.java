package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.carrinho.*;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.CarrinhoItem;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.entity.usuario.Usuario;
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
import java.util.stream.Collectors;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    CarrinhoRepository carrinhoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    IngredienteRepository ingredienteRepository;

    @Override
    public RespostaCarrinhoDTO listarCarrinho() {
        Long usuarioId = authenticatedUser.getId();

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado."));

        RespostaTotaisCarrinhoDTO totais = carrinhoRepository.calcularTotais(carrinho.getId());

        return mapCarrinhoToDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public RespostaCarrinhoDTO adicionarItem(AdicionarCarrinhoItemDTO dto) {
        Long usuarioId = authenticatedUser.getId();

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> criarCarrinho(usuarioId));

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        List<Long> ingredientesId = dto.ingredientesId() != null ? dto.ingredientesId() : List.of();
        List<Ingrediente> ingredientes = ingredienteRepository.findAllById(ingredientesId);

        if (ingredientes.size() != ingredientesId.size()) {
            throw new RuntimeException("Um ou mais ingredientes informados não existem ou estão repetidos.");
        }

        RegraProduto regra = obterRegra(produto);
        regra.validar(produto, ingredientes);

        if (produto.getTipo() == TipoProduto.BEBIDA) {
            CarrinhoItem bebidaExistente = carrinho.getItens().stream().filter(item -> item.getProduto().getId().equals(produto.getId()))
                    .findFirst().orElse(null);

            if (bebidaExistente != null) {
                bebidaExistente.setQuantidade(bebidaExistente.getQuantidade() + 1);
                carrinhoRepository.save(carrinho);
                RespostaTotaisCarrinhoDTO totais = carrinhoRepository.calcularTotais(carrinho.getId());
                return mapCarrinhoToDTO(carrinho, totais);
            }
        }

        CarrinhoItem item = new CarrinhoItem(
                carrinho,
                produto,
                1,
                dto.observacao(),
                new HashSet<>(ingredientes)
        );

        carrinho.getItens().add(item);
        carrinhoRepository.save(carrinho);

        RespostaTotaisCarrinhoDTO totais = carrinhoRepository.calcularTotais(carrinho.getId());
        return mapCarrinhoToDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public RespostaCarrinhoDTO alterarQuantidade(Long itemId, AlterarQuantidadeCarrinhoItemDTO dto) {

        if (dto.quantidade() == null || dto.quantidade() < 1) {
            throw new RuntimeException(("Quantidade mínima permitida é 1."));
        }

        Long usuarioId = authenticatedUser.getId();

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado."));

        CarrinhoItem item = carrinho.getItens().stream().filter(i -> i.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho."));

        if (item.getProduto().getTipo().isMarmita()) {
            throw new RuntimeException("Não é permitido alterar a quantidade de marmitas.");
        }

        item.setQuantidade(dto.quantidade());
        carrinhoRepository.save(carrinho);

        RespostaTotaisCarrinhoDTO totais = carrinhoRepository.calcularTotais(carrinho.getId());
        return mapCarrinhoToDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public void removerItem(Long itemId) {
        Long usuarioId = authenticatedUser.getId();

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado."));

        CarrinhoItem item = carrinho.getItens().stream().filter(i -> i.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho."));

        carrinho.getItens().remove(item);

        carrinhoRepository.save(carrinho);
    }

    @Override
    @Transactional
    public void limparCarrinho() {
        Long usuarioId = authenticatedUser.getId();

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrinho não encotrado."));

        carrinho.getItens().clear();

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

    private RespostaCarrinhoDTO mapCarrinhoToDTO(Carrinho carrinho, RespostaTotaisCarrinhoDTO totais) {

        List<RespostaCarrinhoItemDTO> itens = carrinho.getItens().stream()
                .map(item -> new RespostaCarrinhoItemDTO(
                        item.getId(),
                        item.getProduto().getNome(),
                        item.getProduto().getPrecoUnitario(),
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
                itens,
                totais.getTotalProdutos(),
                totais.getValorTotal()
        );
    }
}
