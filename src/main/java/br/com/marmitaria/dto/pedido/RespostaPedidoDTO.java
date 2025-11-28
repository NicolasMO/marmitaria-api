package br.com.marmitaria.dto.pedido;

import br.com.marmitaria.dto.item.RespostaItemDTO;
import br.com.marmitaria.enums.FormaPagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RespostaPedidoDTO(
    Long pedidoId,
    long usuarioId,
    BigDecimal total,
    String enderecoEntrega,
    String status,
    FormaPagamento formaPagamento,
    List<RespostaItemDTO> itens,
    LocalDateTime dataPedido
) {}