package br.com.marmitaria.dto.produto;

import lombok.Data;

@Data
public class ProdutoDTO {
	private Long id;
	private String nome;
	private String tipo;
	private Double preco;
}
