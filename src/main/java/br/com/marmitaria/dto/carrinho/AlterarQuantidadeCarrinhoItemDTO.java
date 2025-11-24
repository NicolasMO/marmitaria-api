package br.com.marmitaria.dto.carrinho;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AlterarQuantidadeCarrinhoItemDTO(
        @NotNull
        @Min(value = 1, message = "Quantidade mínima permitida: 1")
        @Max(value = 50, message = "Quantidade maxima permitida: 50")
        Integer quantidade
) {}
