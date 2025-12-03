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
        Long usuarioId = contexto.authenticatedUser().getId();
        Carrinho carrinho = contexto.carrinhoValidator().validarExistente(usuarioId);
        RespostaTotaisCarrinhoDTO totais = contexto.carrinhoRepository().calcularTotais(carrinho.getId());
        return contexto.carrinhoMapper().paraDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public RespostaCarrinhoDTO adicionarItem(AdicionarCarrinhoItemDTO dto) {
        Long usuarioId = contexto.authenticatedUser().getId();
        Carrinho carrinho = contexto.carrinhoValidator().obterOuCriar(usuarioId);
        Produto produto = contexto.produtoValidator().validar(dto.produtoId());
        List<Ingrediente> ingredientes = contexto.carrinhoValidator().validarIngredientes(dto.ingredientesId());
        contexto.carrinhoValidator().validarRegrasDoProduto(produto, ingredientes);
        CarrinhoItem item = contexto.carrinhoFactory().criarItem(carrinho, produto, ingredientes, dto.observacao());
        boolean duplicada = contexto.carrinhoValidator().tratarBebidaDuplicada(carrinho, item);

        if (!duplicada) carrinho.getItens().add(item);

        contexto.carrinhoRepository().save(carrinho);
        RespostaTotaisCarrinhoDTO totais = contexto.carrinhoRepository().calcularTotais(carrinho.getId());
        return contexto.carrinhoMapper().paraDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public RespostaCarrinhoDTO alterarQuantidade(Long itemId, AlterarQuantidadeCarrinhoItemDTO dto) {
        Long usuarioId = contexto.authenticatedUser().getId();

        Carrinho carrinho = contexto.carrinhoValidator().validarExistente(usuarioId);

        CarrinhoItem item = contexto.carrinhoValidator().validarItem(carrinho, itemId);

        contexto.carrinhoValidator().validarAlteracaoQuantidade(item, dto.quantidade());

        contexto.carrinhoRepository().save(carrinho);
        RespostaTotaisCarrinhoDTO totais = contexto.carrinhoRepository().calcularTotais(carrinho.getId());
        return contexto.carrinhoMapper().paraDTO(carrinho, totais);
    }

    @Override
    @Transactional
    public void removerItem(Long itemId) {
        Long usuarioId = contexto.authenticatedUser().getId();
        Carrinho carrinho = contexto.carrinhoValidator().validarExistente(usuarioId);
        CarrinhoItem item = contexto.carrinhoValidator().validarItem(carrinho, itemId);
        carrinho.getItens().remove(item);
        contexto.carrinhoRepository().save(carrinho);
    }

    @Override
    @Transactional
    public void limparCarrinho() {
        Long usuarioId = contexto.authenticatedUser().getId();
        Carrinho carrinho = contexto.carrinhoValidator().validarExistente(usuarioId);
        carrinho.getItens().clear();
        contexto.carrinhoRepository().save(carrinho);
    }

    @Override
    @Transactional
    public void removerCarrinho(Carrinho carrinho) {
        Long usuarioId = contexto.authenticatedUser().getId();
        contexto.carrinhoValidator().validarExclusaoCarrinho(carrinho, usuarioId);
        contexto.carrinhoRepository().delete(carrinho);
    }
}
