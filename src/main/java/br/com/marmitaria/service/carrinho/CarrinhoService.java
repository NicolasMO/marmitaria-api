package br.com.marmitaria.service.carrinho;

import java.util.List;

import br.com.marmitaria.dto.carrinho.AdicionarItemCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.ItemCarrinhoDTO;

public interface CarrinhoService {
	public List<ItemCarrinhoDTO> listarItensDoCarrinho(Long carrinhoId);
	public void adicionarItemAoCarrinho(AdicionarItemCarrinhoDTO dto);
	public void limparCarrinho(Long carrinhoId);
	public void removerItemDoCarrinho(Long itemId, Long carrinhoId);
}
