package br.com.marmitaria.dto.pedido;

import java.math.BigDecimal;
import java.util.Set;

public record RespostaPedidoItemDTO(
        Long id,
        String produtoNome,
        BigDecimal precoUnitario,
        Integer quantidade,
        String observacao,
        Set<String> ingredientes
) {}