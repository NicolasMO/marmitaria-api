package br.com.marmitaria.dto.carrinho;

import java.math.BigDecimal;

public interface RespostaTotaisCarrinhoDTO{
        Integer getTotalProdutos();
        BigDecimal getValorTotal();
}