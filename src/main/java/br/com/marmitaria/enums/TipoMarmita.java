package br.com.marmitaria.enums;

import lombok.Getter;

@Getter
public enum TipoMarmita {
	PEQUENA(1, 2, 2),
	GRANDE(2, 3, 4);
	
	private final int maxProteinas;
	private final int maxCarboidratos;
	private final int maxComplementos;
	
	TipoMarmita(int maxProteinas, int maxCarboidratos, int maxComplementos) {
		this.maxProteinas = maxProteinas;
		this.maxCarboidratos = maxCarboidratos;
		this.maxComplementos = maxComplementos;
	}
}
