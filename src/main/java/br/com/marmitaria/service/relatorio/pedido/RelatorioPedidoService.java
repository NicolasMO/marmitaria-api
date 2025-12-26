package br.com.marmitaria.service.relatorio.pedido;

import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


public interface RelatorioPedidoService {
    Page<RelatorioPedidoDTO> gerarRelatorio(LocalDate inicio, LocalDate fim, Pageable paginacao);
}
