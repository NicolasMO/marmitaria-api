package br.com.marmitaria.factory;

import br.com.marmitaria.dto.ingrediente.CadastroIngredienteDTO;
import br.com.marmitaria.dto.ingrediente.RespostaIngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.enums.CategoriaIngrediente;

public class IngredienteFactoryTeste {

    public static CadastroIngredienteDTO criarCadastroIngredienteDTO() {
        return new CadastroIngredienteDTO(
                "Peito de frango",
                CategoriaIngrediente.PROTEINA
        );
    }

    public static Ingrediente criarIngredienteProteina() {
        Ingrediente ingrediente = new Ingrediente(
                "Peito de Frango",
                CategoriaIngrediente.PROTEINA
        );
        ingrediente.setId(1L);
        return ingrediente;
    }

    public static RespostaIngredienteDTO criarRespostaIngredienteDTO() {
        return new RespostaIngredienteDTO(
          1L,
          "Peito de Frango",
          CategoriaIngrediente.PROTEINA
        );
    }
}
