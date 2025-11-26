package br.com.marmitaria.dto.carrinho;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AdicionarCarrinhoItemDTO(
        @NotNull
        Long produtoId,

        List<Long> ingredientesId,

        String observacao
) {}
