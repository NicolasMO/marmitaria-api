package br.com.marmitaria.dto.produto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MarmitaDTO {
	private List<Long> ingredientesId;
}
