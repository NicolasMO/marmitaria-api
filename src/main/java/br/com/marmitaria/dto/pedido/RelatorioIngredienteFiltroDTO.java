package br.com.marmitaria.dto.pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record RelatorioIngredienteFiltroDTO(
        @NotEmpty(message = "Informe ao menos um ingrediente")

        List<@NotBlank(message = "Nome do ingrediente não pode ser vazio")
                @Pattern(
                        regexp = "^[A-Za-zÀ-ÿ0-9]+(?:[ -][A-Za-zÀ-ÿ0-9]+)*$"
                )
        String> ingrediente
) {}