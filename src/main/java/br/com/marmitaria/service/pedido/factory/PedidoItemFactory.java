package br.com.marmitaria.service.pedido.factory;

import br.com.marmitaria.entity.carrinho.CarrinhoItem;
import br.com.marmitaria.entity.pedido.Pedido;
import br.com.marmitaria.entity.pedido.PedidoItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoItemFactory {

    public List<PedidoItem> criarItens(Pedido pedido, List<CarrinhoItem> itens) {
        return itens.stream()
                .map(item -> new PedidoItem(
                        pedido,
                        item.getProduto(),
                        item.getQuantidade(),
                        item.getObservacao(),
                        item.getIngredientes()
                ))
                .toList();
    }

}
