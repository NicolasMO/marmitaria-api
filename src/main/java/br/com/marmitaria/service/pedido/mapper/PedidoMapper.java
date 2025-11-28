package br.com.marmitaria.service.pedido.mapper;

import br.com.marmitaria.dto.pedido.RespostaPedidoDTO;
import br.com.marmitaria.entity.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    @Autowired
    PedidoItemMapper itemMapper;

    public RespostaPedidoDTO paraDTO(Pedido pedido) {
        return new RespostaPedidoDTO(
                pedido.getId(),
                pedido.getUsuario().getId(),
                pedido.getTotal(),
                pedido.getEnderecoEntrega(),
                pedido.getStatus(),
                pedido.getFormaPagamento(),
                pedido.getItens().stream()
                        .map(itemMapper::paraDTO)
                        .toList(),
                pedido.getDataPedido()
        );
    }
}
