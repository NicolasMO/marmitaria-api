package br.com.marmitaria.service.relatorio.pedido;

import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RelatorioPedidoService {
    List<RelatorioPedidoDTO> gerarRelatorio(LocalDateTime inicio, LocalDateTime fim);
}
