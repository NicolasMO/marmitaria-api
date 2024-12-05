package br.com.marmitaria.service.carrinho;

import java.util.List;

import br.com.marmitaria.dto.carrinho.AdicionarItemCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.ItemCarrinhoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;

public interface CarrinhoService {
	 Carrinho buscarCarrinhoPorUsuario(Long usuarioId);
	public List<ItemCarrinhoDTO> listarItensCarrinho(Long carrinhoId);
	public void adicionarItemCarrinho(AdicionarItemCarrinhoDTO dto);
}
