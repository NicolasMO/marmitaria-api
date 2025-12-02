package br.com.marmitaria.service.pedido.mapper;

import br.com.marmitaria.dto.pedido.RespostaPedidoDTO;
import br.com.marmitaria.entity.pedido.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoMapper {

    private final PedidoItemMapper itemMapper;

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
