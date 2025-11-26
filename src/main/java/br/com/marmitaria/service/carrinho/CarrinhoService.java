package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.dto.carrinho.AdicionarCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.AlterarQuantidadeCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.RespostaCarrinhoDTO;

public interface CarrinhoService {
    RespostaCarrinhoDTO listarCarrinho();
    RespostaCarrinhoDTO adicionarItem(AdicionarCarrinhoItemDTO dto);
    RespostaCarrinhoDTO alterarQuantidade(Long itemId, AlterarQuantidadeCarrinhoItemDTO dto);
    void removerItem(Long itemId);
    void limparCarrinho();
}
