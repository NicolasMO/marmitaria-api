package br.com.marmitaria.service.carrinho.factory;

import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.CarrinhoItem;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class CarrinhoFactory {

    public CarrinhoItem criarItem(
            Carrinho carrinho,
            Produto produto,
            List<Ingrediente> ingredientes,
            String observacao
    ) {
        return new CarrinhoItem(
                carrinho,
                produto,
                1,
                observacao,
                new HashSet<>(ingredientes)
        );
    }
}
