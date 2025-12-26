package br.com.marmitaria.service.relatorio.pedido;

import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import br.com.marmitaria.entity.pedido.Pedido;
import br.com.marmitaria.repository.pedido.PedidoRepository;
import br.com.marmitaria.service.relatorio.pedido.mapper.RelatorioPedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RelatorioPedidoServiceImpl implements RelatorioPedidoService {

    private final PedidoRepository pedidoRepository;
    private final RelatorioPedidoMapper relatorioMapper;

    public Page<RelatorioPedidoDTO> gerarRelatorio(LocalDate inicio, LocalDate fim, Pageable paginacao) {
        LocalDateTime inicioDia = inicio.atStartOfDay();
        LocalDateTime fimDia = fim.atTime(23, 59, 59);
        Page<Pedido> pedidos = pedidoRepository.findByDataPedidoBetween(inicioDia, fimDia, paginacao);

        return pedidos.map(relatorioMapper::paraDTO);
    }

}
