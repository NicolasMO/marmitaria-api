package br.com.marmitaria.dto.produto;

import br.com.marmitaria.enums.TipoProduto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CadastroProdutoDTO(
        @NotBlank
        String nome,

        @NotNull
        @Digits(integer = 4, fraction = 2)
        @DecimalMin(value = "0.00", message = "O preço unitário deve ser 0.00 ou maior.")
        @DecimalMax(value = "9999.99", message = "O preço unitário não pode ser maior que 9999.99.")
        BigDecimal precoUnitario,

        @NotNull
        TipoProduto tipo
) {}
