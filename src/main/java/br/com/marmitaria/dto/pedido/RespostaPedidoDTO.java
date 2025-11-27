package br.com.marmitaria.dto.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RespostaPedidoDTO(
    Long pedidoId,
    long usuarioId,
    BigDecimal total,
    String status,
    String formaPagamento,
    List<RespostaPedidoItemDTO> itens,
    LocalDateTime dataPedido
) {}