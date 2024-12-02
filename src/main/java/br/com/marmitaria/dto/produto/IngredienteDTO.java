package br.com.marmitaria.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredienteDTO {
	private Long id;
	private String nome;
	private String categoria;
}
