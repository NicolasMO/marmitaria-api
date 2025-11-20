package br.com.marmitaria.dto.produto;

import br.com.marmitaria.enums.TipoProduto;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AtualizarProdutoDTO(
        @NotBlank
        String nome,

        @NotNull
        @Digits(integer = 4, fraction = 2)
        @DecimalMin("0.00")
        @DecimalMax("9999.99")
        BigDecimal preco_unitario,

        @NotNull
        TipoProduto tipo
) {}
