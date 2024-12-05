package br.com.marmitaria.dto.produto;

import br.com.marmitaria.enums.CategoriaIngrediente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredienteDTO {
	private Long id;
	private String nome;
	private CategoriaIngrediente categoria;
}
