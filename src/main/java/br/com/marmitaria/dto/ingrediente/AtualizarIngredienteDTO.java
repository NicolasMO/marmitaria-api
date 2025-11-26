package br.com.marmitaria.dto.ingrediente;

import br.com.marmitaria.enums.CategoriaIngrediente;

public record AtualizarIngredienteDTO(
        String nome,
        CategoriaIngrediente categoria
) {}