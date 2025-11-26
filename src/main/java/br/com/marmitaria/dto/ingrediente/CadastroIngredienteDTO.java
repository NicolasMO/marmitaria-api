package br.com.marmitaria.dto.ingrediente;

import br.com.marmitaria.enums.CategoriaIngrediente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroIngredienteDTO(
        @NotBlank
        String nome,

        @NotNull
        CategoriaIngrediente categoria
) {}