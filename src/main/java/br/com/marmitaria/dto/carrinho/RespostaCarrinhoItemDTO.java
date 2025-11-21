package br.com.marmitaria.dto.carrinho;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record RespostaCarrinhoItemDTO(
        Long id,
        String produtoNome,
        BigDecimal precoUnitario,
        Integer quantidade,
        String observacao,
        Set<String> ingredientes
) {}