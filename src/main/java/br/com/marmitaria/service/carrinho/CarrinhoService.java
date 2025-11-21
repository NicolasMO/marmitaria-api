package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.dto.carrinho.AdicionarCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.RespostaCarrinhoDTO;
import jakarta.validation.Valid;

public interface CarrinhoService {
    RespostaCarrinhoDTO listarCarrinho(Long usuarioId);
    RespostaCarrinhoDTO adicionarItem(Long usuarioId,AdicionarCarrinhoItemDTO dto);
    void removerItem(Long usuarioId, Long itemId);
}
