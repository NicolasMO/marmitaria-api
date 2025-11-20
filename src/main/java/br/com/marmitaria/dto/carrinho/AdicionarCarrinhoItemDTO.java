package br.com.marmitaria.dto.carrinho;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AdicionarCarrinhoItemDTO(
        @NotNull
        Long produtoId,

        @NotNull
        @Min(1)
        @Max(50)
        Integer quantidade,

        List<Long> ingredientesId,

        String observacao
) {}
