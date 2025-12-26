package br.com.marmitaria.dto.pedido;

import br.com.marmitaria.dto.item.RelatorioPedidoItemDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RelatorioPedidoDTO(
        Long pedidoId,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataPedido,
        List<RelatorioPedidoItemDTO> itens,
        BigDecimal valorTotalPedido,
        String endereco
) {}