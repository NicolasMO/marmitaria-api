package br.com.marmitaria.dto.produto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MarmitaDTO {
	private Long produtoId;
	private List<Long> ingredientesId;
}
