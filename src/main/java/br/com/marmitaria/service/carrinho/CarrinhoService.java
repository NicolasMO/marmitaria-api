package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.dto.carrinho.AdicionarItemCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.CarrinhoResponseDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;

public interface CarrinhoService {
	public CarrinhoResponseDTO listarItensDoCarrinho(Long carrinhoId);
	public void adicionarItemAoCarrinho(AdicionarItemCarrinhoDTO dto);
	public void limparCarrinho(Long carrinhoId);
	public void removerItemDoCarrinho(Long itemId, Long carrinhoId);
	public Carrinho criarCarrinhoParaUsuario(Long usuarioId);
}
