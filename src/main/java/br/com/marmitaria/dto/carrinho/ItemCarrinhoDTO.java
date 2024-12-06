package br.com.marmitaria.dto.carrinho;

import br.com.marmitaria.dto.produto.ProdutoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarrinhoDTO {
	private Long id;
	private ProdutoDTO produto;
	private Integer quantidade;
	private Double preco;
	}