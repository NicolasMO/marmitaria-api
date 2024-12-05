package br.com.marmitaria.dto.produto;

import java.util.List;

import br.com.marmitaria.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
	private Long id;
	private String nome;
	private TipoProduto tipo;
	private Double preco;
	private List<IngredienteDTO> ingredientes; 
}
