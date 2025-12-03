package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.dto.carrinho.*;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.CarrinhoItem;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.exception.carrinho.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarrinhoServiceImpl implements CarrinhoService {

    private final CarrinhoContext contexto;

    @Override
    public RespostaCarrinhoDTO listarCarrinho() {
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        Carrinho carrinho = contexto.getCarrinhoValidator().validarExistente(usuarioId);
        RespostaTotaisCarrinhoDTO totais = contexto.getCarrinhoRepository().calcularTotais(carrinho.getId());
        return contexto.getCarrinhoMapper().paraDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public RespostaCarrinhoDTO adicionarItem(AdicionarCarrinhoItemDTO dto) {
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        Carrinho carrinho = contexto.getCarrinhoValidator().obterOuCriar(usuarioId);
        Produto produto = contexto.getProdutoValidator().validar(dto.produtoId());
        List<Ingrediente> ingredientes = contexto.getCarrinhoValidator().validarIngredientes(dto.ingredientesId());
        contexto.getCarrinhoValidator().validarRegrasDoProduto(produto, ingredientes);
        CarrinhoItem item = contexto.getCarrinhoFactory().criarItem(carrinho, produto, ingredientes, dto.observacao());
        boolean duplicada = contexto.getCarrinhoValidator().tratarBebidaDuplicada(carrinho, item);

        if (!duplicada) carrinho.getItens().add(item);

        contexto.getCarrinhoRepository().save(carrinho);
        RespostaTotaisCarrinhoDTO totais = contexto.getCarrinhoRepository().calcularTotais(carrinho.getId());
        return contexto.getCarrinhoMapper().paraDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public RespostaCarrinhoDTO alterarQuantidade(Long itemId, AlterarQuantidadeCarrinhoItemDTO dto) {
        Long usuarioId = contexto.getAuthenticatedUser().getId();

        Carrinho carrinho = contexto.getCarrinhoValidator().validarExistente(usuarioId);

        CarrinhoItem item = contexto.getCarrinhoValidator().validarItem(carrinho, itemId);

        contexto.getCarrinhoValidator().validarAlteracaoQuantidade(item, dto.quantidade());

        contexto.getCarrinhoRepository().save(carrinho);
        RespostaTotaisCarrinhoDTO totais = contexto.getCarrinhoRepository().calcularTotais(carrinho.getId());
        return contexto.getCarrinhoMapper().paraDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public void removerItem(Long itemId) {
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        Carrinho carrinho = contexto.getCarrinhoValidator().validarExistente(usuarioId);
        CarrinhoItem item = contexto.getCarrinhoValidator().validarItem(carrinho, itemId);
        carrinho.getItens().remove(item);
        contexto.getCarrinhoRepository().save(carrinho);
    }

    @Override
    @Transactional
    public void limparCarrinho() {
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        Carrinho carrinho = contexto.getCarrinhoValidator().validarExistente(usuarioId);
        carrinho.getItens().clear();
        contexto.getCarrinhoRepository().save(carrinho);
    }

    @Override
    @Transactional
    public void removerCarrinho(Carrinho carrinho) {
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        contexto.getCarrinhoValidator().validarExclusaoCarrinho(carrinho, usuarioId);
        contexto.getCarrinhoRepository().delete(carrinho);
    }
}
