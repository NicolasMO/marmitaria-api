package br.com.marmitaria.service.pedido.factory;

import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.pedido.Pedido;
import br.com.marmitaria.entity.pedido.PedidoItem;
import br.com.marmitaria.enums.FormaPagamento;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoFactory {

    private final PedidoItemFactory itemFactory;
    private final CarrinhoRepository carrinhoRepository;

    public Pedido criarPedido(Carrinho carrinho, Endereco endereco, FormaPagamento formaPagamento) {

        Pedido pedido = new Pedido(
                carrinho.getUsuario(),
                endereco.getLogradouro() + ", " + endereco.getNumero(),
                formaPagamento
        );

        List<PedidoItem> itens = itemFactory.criarItens(pedido, carrinho.getItens());
        pedido.getItens().addAll(itens);


        BigDecimal total = carrinhoRepository   .calcularTotais(carrinho.getId()).getValorTotal();
        pedido.setTotal(total);

        return pedido;
    }
}
