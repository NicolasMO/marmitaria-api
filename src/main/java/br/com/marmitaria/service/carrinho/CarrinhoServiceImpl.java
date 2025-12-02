package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.dto.carrinho.*;
import br.com.marmitaria.dto.item.RespostaItemDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.CarrinhoItem;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.enums.TipoProduto;
import br.com.marmitaria.exception.carrinho.*;
import br.com.marmitaria.exception.ingrediente.IngredienteListaNaoEncontradaException;
import br.com.marmitaria.exception.produto.ProdutoTipoInvalidoException;
import br.com.marmitaria.exception.usuario.UsuarioNaoEncontradoException;
import br.com.marmitaria.service.carrinho.strategy.RegraBebida;
import br.com.marmitaria.service.carrinho.strategy.RegraMarmita;
import br.com.marmitaria.service.carrinho.strategy.RegraProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarrinhoServiceImpl implements CarrinhoService {

    private final CarrinhoContext contexto;

    @Override
    public RespostaCarrinhoDTO listarCarrinho() {
        Long usuarioId = contexto.authenticatedUser.getId();
        Carrinho carrinho = contexto.carrinhoValidator.validar(usuarioId);
        RespostaTotaisCarrinhoDTO totais = contexto.carrinhoRepository.calcularTotais(carrinho.getId());
        return mapCarrinhoToDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public RespostaCarrinhoDTO adicionarItem(AdicionarCarrinhoItemDTO dto) {
        Long usuarioId = contexto.authenticatedUser.getId();

        Carrinho carrinho = contexto.carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> criarCarrinho(usuarioId));

        Produto produto = contexto.produtoValidator.validar(dto.produtoId());

        List<Long> ingredientesId = dto.ingredientesId() != null ? dto.ingredientesId() : List.of();
        List<Ingrediente> ingredientes = contexto.ingredienteRepository.findAllById(ingredientesId);

        if (ingredientes.size() != ingredientesId.size()) {
            throw new IngredienteListaNaoEncontradaException();
        }

        RegraProduto regra = obterRegra(produto);
        regra.validar(produto, ingredientes);

        if (produto.getTipo() == TipoProduto.BEBIDA) {
            CarrinhoItem bebidaExistente = carrinho.getItens().stream().filter(item -> item.getProduto().getId().equals(produto.getId()))
                    .findFirst().orElse(null);

            if (bebidaExistente != null) {
                bebidaExistente.setQuantidade(bebidaExistente.getQuantidade() + 1);
                contexto.carrinhoRepository.save(carrinho);
                RespostaTotaisCarrinhoDTO totais = contexto.carrinhoRepository.calcularTotais(carrinho.getId());
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
        contexto.carrinhoRepository.save(carrinho);

        RespostaTotaisCarrinhoDTO totais = contexto.carrinhoRepository.calcularTotais(carrinho.getId());
        return mapCarrinhoToDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public RespostaCarrinhoDTO alterarQuantidade(Long itemId, AlterarQuantidadeCarrinhoItemDTO dto) {

        if (dto.quantidade() == null || dto.quantidade() < 1) {
            throw new CarrinhoAlterarQuantidadeBebidaException("mínima", 1);
        }

        Long usuarioId = contexto.authenticatedUser.getId();

        Carrinho carrinho = contexto.carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException());

        CarrinhoItem item = carrinho.getItens().stream().filter(i -> i.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new CarrinhoItemNaoEncontradoException());

        if (item.getProduto().getTipo().isMarmita()) {
            throw new CarrinhoAlterarQuantidadeMarmitaException();
        }

        item.setQuantidade(dto.quantidade());
        contexto.carrinhoRepository.save(carrinho);

        RespostaTotaisCarrinhoDTO totais = contexto.carrinhoRepository.calcularTotais(carrinho.getId());
        return mapCarrinhoToDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public void removerItem(Long itemId) {
        Long usuarioId = contexto.authenticatedUser.getId();

        Carrinho carrinho = contexto.carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException());

        CarrinhoItem item = carrinho.getItens().stream().filter(i -> i.getId().equals(itemId))
                .findFirst().orElseThrow(() -> new CarrinhoItemNaoEncontradoException());

        carrinho.getItens().remove(item);

        contexto.carrinhoRepository.save(carrinho);
    }

    @Override
    @Transactional
    public void limparCarrinho() {
        Long usuarioId = contexto.authenticatedUser.getId();

        Carrinho carrinho = contexto.carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException());

        carrinho.getItens().clear();

        contexto.carrinhoRepository.save(carrinho);
    }

    @Override
    @Transactional
    public void removerCarrinho(Carrinho carrinho) {
        Long usuarioId = contexto.authenticatedUser.getId();

        Carrinho carrinhoEncontrado = contexto.carrinhoRepository.findById(carrinho.getId())
                .orElseThrow(() -> new CarrinhoNaoEncontradoException());

        if(!carrinhoEncontrado.getUsuario().getId().equals(usuarioId)) {
            throw new CarrinhoUsuarioSemPermissaoException("Você não tem permissão para remover este carrinho");
        }

        contexto.carrinhoRepository.delete(carrinhoEncontrado);
    }

    // Metodos Privados

    private Carrinho criarCarrinho(Long usuarioId) {
        Usuario usuario = contexto.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        Carrinho carrinho = new Carrinho(usuario, new ArrayList<>());

        return contexto.carrinhoRepository.save(carrinho);
    }

    private RegraProduto obterRegra(Produto produto) {
        return switch (produto.getTipo()) {
            case BEBIDA -> new RegraBebida();
            case MARMITA_PEQUENA, MARMITA_GRANDE -> new RegraMarmita();
            default -> throw new ProdutoTipoInvalidoException();
        };
    }

    private RespostaCarrinhoDTO mapCarrinhoToDTO(Carrinho carrinho, RespostaTotaisCarrinhoDTO totais) {

        List<RespostaItemDTO> itens = carrinho.getItens().stream()
                .map(item -> new RespostaItemDTO(
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
