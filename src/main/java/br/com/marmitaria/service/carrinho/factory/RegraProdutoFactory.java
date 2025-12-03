package br.com.marmitaria.service.carrinho.factory;

import br.com.marmitaria.enums.TipoProduto;
import br.com.marmitaria.exception.produto.ProdutoTipoInvalidoException;
import br.com.marmitaria.service.carrinho.strategy.RegraBebida;
import br.com.marmitaria.service.carrinho.strategy.RegraMarmita;
import br.com.marmitaria.service.carrinho.strategy.RegraProduto;
import org.springframework.stereotype.Component;

@Component
public class RegraProdutoFactory {

    public static RegraProduto criar(TipoProduto tipo) {
        return switch (tipo) {
            case BEBIDA -> new RegraBebida();
            case MARMITA_PEQUENA, MARMITA_GRANDE -> new RegraMarmita();
            default -> throw new ProdutoTipoInvalidoException();
        };
    }
}
