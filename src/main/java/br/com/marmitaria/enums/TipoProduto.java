package br.com.marmitaria.enums;

import lombok.Getter;

@Getter
public enum TipoProduto {
	MARMITA_PEQUENA,
    MARMITA_GRANDE,
    BEBIDA;

    public boolean isMarmita() {
        return this == MARMITA_PEQUENA || this == MARMITA_GRANDE;
    }
}
