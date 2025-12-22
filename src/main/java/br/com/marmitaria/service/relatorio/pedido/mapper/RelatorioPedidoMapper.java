package br.com.marmitaria.service.relatorio.pedido.mapper;

import br.com.marmitaria.dto.item.RelatorioPedidoItemDTO;
import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.pedido.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RelatorioPedidoMapper {

    public RelatorioPedidoDTO paraDTO(Pedido pedido) {
        return new RelatorioPedidoDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                mapItens(pedido),
                pedido.getTotal(),
                pedido.getEnderecoEntrega()
        );
    }

    public List<RelatorioPedidoDTO> paraListaDTO(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::paraDTO)
                .toList();
    }

    private List<RelatorioPedidoItemDTO> mapItens(Pedido pedido) {
        return pedido.getItens().stream()
                .map(item -> new RelatorioPedidoItemDTO(
                        item.getProduto().getNome(),
                        item.getIngredientes().stream()
                                .map(Ingrediente::getNome)
                                .toList(),
                        item.getQuantidade(),
                        item.getProduto().getPrecoUnitario(),
                        item.getPedido().getTotal()
                ))
                .toList();
    }
}
