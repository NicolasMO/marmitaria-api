package br.com.marmitaria.dto.item;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioPedidoItemDTO(
    String produto,
    List<String> ingredientes,
    Integer quantidade,
    BigDecimal valorUnitario,
    BigDecimal valorTotalItem
) {}