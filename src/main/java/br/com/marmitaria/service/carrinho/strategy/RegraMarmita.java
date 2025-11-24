package br.com.marmitaria.service.carrinho.strategy;

import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.enums.CategoriaIngrediente;
import br.com.marmitaria.enums.RegrasMarmita;

import java.util.List;

public class RegraMarmita implements RegraProduto {

    @Override
    public void validar(Produto produto, List<Ingrediente> ingredientes) {
        if (ingredientes.isEmpty()) {
            throw new RuntimeException("Marmitas devem conter ingredientes.");
        }

        RegrasMarmita regras = RegrasMarmita.of(produto.getTipo());

        long proteina = ingredientes.stream().filter(i -> i.getCategoria() == CategoriaIngrediente.PROTEINA).count();
        long carboidrato = ingredientes.stream().filter(i -> i.getCategoria() == CategoriaIngrediente.CARBOIDRATO).count();
        long complemento = ingredientes.stream().filter(i -> i.getCategoria() == CategoriaIngrediente.COMPLEMENTO).count();

        var limite = regras.limites;

        if (proteina < limite.minProteina() || proteina > limite.maxProteina())
            throw new RuntimeException("Quantidade inválida de proteínas.");

        if (carboidrato < limite.minCarboidrato() || carboidrato > limite.maxCarboidrato())
            throw new RuntimeException("Quantidade inválida de carboidratos.");

        if (complemento < limite.minComplemento() || complemento > limite.maxComplemento())
            throw new RuntimeException("Quantidade inválida de complementos.");
    }
}
