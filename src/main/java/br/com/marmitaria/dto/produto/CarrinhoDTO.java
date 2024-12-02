package br.com.marmitaria.dto.produto;

import java.util.List;

import lombok.Data;

@Data
public class CarrinhoDTO {
	private Long clienteId;
	private List<Long> produtosId;
}
