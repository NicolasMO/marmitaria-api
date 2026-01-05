package br.com.marmitaria.service.relatorio.pedido.mapper;

import br.com.marmitaria.dto.item.RelatorioPedidoItemDTO;
import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.pedido.Pedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class RelatorioPedidoMapper {

    public RelatorioPedidoDTO paraDTO(Pedido pedido) {
        return new RelatorioPedidoDTO(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getUsuario().getNome(),
                mapearItens(pedido),
                pedido.getTotal(),
                pedido.getEnderecoEntrega()
        );
    }

    public List<RelatorioPedidoDTO> paraListaDTO(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::paraDTO)
                .toList();
    }

    private List<RelatorioPedidoItemDTO> mapearItens(Pedido pedido) {
        return pedido.getItens().stream()
                .map(item -> {
                    BigDecimal valorUnitario = item.getProduto().getPrecoUnitario();
                    BigDecimal valorTotalItem = valorUnitario.multiply(BigDecimal.valueOf(item.getQuantidade()));

                    return new RelatorioPedidoItemDTO(
                            item.getProduto().getNome(),
                            item.getIngredientes().stream().map(Ingrediente::getNome).toList(),
                            item.getQuantidade(),
                            valorUnitario,
                            valorTotalItem
                    );
                }).toList();
    }
}
