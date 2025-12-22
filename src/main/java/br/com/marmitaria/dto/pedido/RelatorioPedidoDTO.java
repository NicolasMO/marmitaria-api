package br.com.marmitaria.dto.pedido;

import br.com.marmitaria.dto.item.RelatorioPedidoItemDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RelatorioPedidoDTO(
        Long pedidoId,
        LocalDateTime dataPedido,
        List<RelatorioPedidoItemDTO> itens,
        BigDecimal valorTotal,
        String endereco
) {}