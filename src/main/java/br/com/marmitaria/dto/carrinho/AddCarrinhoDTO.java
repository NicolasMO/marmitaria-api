package br.com.marmitaria.dto.carrinho;

import lombok.Data;

@Data
public class AddCarrinhoDTO {
	private Long usuarioId;
	private Long produtoId;
	private Integer quantidade;
}
