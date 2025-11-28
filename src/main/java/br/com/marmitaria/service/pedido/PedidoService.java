package br.com.marmitaria.service.pedido;

import br.com.marmitaria.dto.pedido.ConcluirPedidoDTO;
import br.com.marmitaria.dto.pedido.RespostaPedidoDTO;

public interface PedidoService {
    RespostaPedidoDTO buscarPorId(Long id);
    RespostaPedidoDTO concluirPedido(ConcluirPedidoDTO dto);
}
