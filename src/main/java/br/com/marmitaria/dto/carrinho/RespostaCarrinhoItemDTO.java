package br.com.marmitaria.dto.carrinho;

import java.math.BigDecimal;
import java.util.Set;

public record RespostaCarrinhoItemDTO(
        Long id,
        String produtoNome,
        BigDecimal precoUnitario,
        Integer quantidade,
        String observacao,
        Set<String> ingredientes
) {}