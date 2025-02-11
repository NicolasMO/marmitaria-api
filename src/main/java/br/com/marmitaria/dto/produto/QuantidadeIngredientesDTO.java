package br.com.marmitaria.dto.produto;

import br.com.marmitaria.enums.CategoriaIngrediente;
import br.com.marmitaria.enums.QuantidadeIngredientes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantidadeIngredientesDTO {
    private int produtoId;
    private int maxProteinas;
    private int maxCarboidratos;
    private int maxComplementos;

    public QuantidadeIngredientesDTO(QuantidadeIngredientes quantidade) {
        this.produtoId = quantidade.getProdutoId();
        this.maxProteinas = quantidade.getMaxPorCategoria(CategoriaIngrediente.PROTEINA);
        this.maxCarboidratos = quantidade.getMaxPorCategoria(CategoriaIngrediente.CARBOIDRATO);
        this.maxComplementos = quantidade.getMaxPorCategoria(CategoriaIngrediente.COMPLEMENTO);
    }
}
