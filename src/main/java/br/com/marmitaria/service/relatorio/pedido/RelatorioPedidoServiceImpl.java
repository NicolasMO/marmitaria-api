package br.com.marmitaria.service.relatorio.pedido;

import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import br.com.marmitaria.entity.pedido.Pedido;
import br.com.marmitaria.repository.pedido.PedidoRepository;
import br.com.marmitaria.service.relatorio.pedido.mapper.RelatorioPedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioPedidoServiceImpl implements RelatorioPedidoService {

    private final PedidoRepository pedidoRepository;
    private final RelatorioPedidoMapper relatorioMapper;

    public List<RelatorioPedidoDTO> gerarRelatorio(LocalDateTime inicio, LocalDateTime fim) {
        List<Pedido> pedidos = pedidoRepository.findByDataPedidoBetween(inicio, fim);

        return relatorioMapper.paraListaDTO(pedidos);
    }

}
