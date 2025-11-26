package br.com.marmitaria.service.carrinho.strategy;

import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;

import java.util.List;

public interface RegraProduto {
    void validar(Produto produto, List<Ingrediente> ingredientes);
}
