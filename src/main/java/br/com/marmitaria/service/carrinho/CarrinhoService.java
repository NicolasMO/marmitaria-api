package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.dto.carrinho.AddCarrinhoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;

public interface CarrinhoService {
	public Carrinho adicionarItemCarrinho(AddCarrinhoDTO addCarrinhoDTO);
}
