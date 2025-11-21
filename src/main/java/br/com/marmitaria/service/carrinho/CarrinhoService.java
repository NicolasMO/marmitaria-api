package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.dto.carrinho.AdicionarCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.AlterarQuantidadeCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.RespostaCarrinhoDTO;
import jakarta.validation.Valid;

public interface CarrinhoService {
    RespostaCarrinhoDTO listarCarrinho(Long usuarioId);
    RespostaCarrinhoDTO adicionarItem(Long usuarioId,AdicionarCarrinhoItemDTO dto);
    RespostaCarrinhoDTO alterarQuantidade(Long usuarioId, Long itemId, AlterarQuantidadeCarrinhoItemDTO dto);
    void removerItem(Long usuarioId, Long itemId);
    void limparCarrinho(Long usuarioId);
}
