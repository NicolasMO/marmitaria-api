package br.com.marmitaria.dto.ingrediente;

import br.com.marmitaria.enums.CategoriaIngrediente;

public record RespostaIngredienteDTO(
        Long id,
        String nome,
        CategoriaIngrediente categoria
) {}