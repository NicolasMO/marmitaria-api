package br.com.marmitaria.service.carrinho.strategy;

import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.exception.produto.ProdutoComIngredienteException;

import java.util.List;

public class RegraBebida implements RegraProduto {

    @Override
    public void validar(Produto produto, List<Ingrediente> ingredientes) {
        if (!ingredientes.isEmpty()) {
            throw new ProdutoComIngredienteException("Bebidas não podem ter ingredientes.");
        }
    }
}
