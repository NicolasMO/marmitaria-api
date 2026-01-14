package br.com.marmitaria.service.relatorio.pedido;

import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


public interface RelatorioPedidoService {
    Page<RelatorioPedidoDTO> gerarRelatorio(LocalDate inicio, LocalDate fim, Pageable paginacao);
    Page<RelatorioPedidoDTO> gerarRelatorioPorUsuario(Long usuarioId, Pageable paginacao);
    Page<RelatorioPedidoDTO> gerarRelatorioDoUsuario(Pageable paginacao);
    Page<RelatorioPedidoDTO> gerarRelatorioPorProdutos(List<String> produtos, Pageable paginacao);
    Page<RelatorioPedidoDTO> gerarRelatorioPorIngredientes(List<String> ingredientes, Pageable paginacao);
}
