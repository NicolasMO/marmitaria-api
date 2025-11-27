package br.com.marmitaria.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum FormaPagamento {
	PIX,
    CREDITO,
    DEBITO,
    DINHEIRO;

    @JsonCreator
    public static FormaPagamento from(String value) {
        try {
            return FormaPagamento.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Forma de pagamento inválida. Use: PIX, CREDITO, DEBITO ou DINHEIRO.");
        }
    }
}
