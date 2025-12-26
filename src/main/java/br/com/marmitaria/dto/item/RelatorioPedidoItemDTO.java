package br.com.marmitaria.dto.item;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record RelatorioPedidoItemDTO(
    String produto,
    List<String> ingredientes,
    Integer quantidade,
    BigDecimal valorUnitario,
    BigDecimal valorTotalItem
) {}