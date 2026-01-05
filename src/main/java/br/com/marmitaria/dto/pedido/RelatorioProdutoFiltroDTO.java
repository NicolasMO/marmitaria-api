package br.com.marmitaria.dto.pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record RelatorioProdutoFiltroDTO(
        @NotEmpty(message = "Informe ao menos um produto")

        List<@NotBlank(message = "Nome do produto não pode ser vazio")
                @Pattern(
                        regexp = "^[A-Za-zÀ-ÿ0-9]+(?:[ -][A-Za-zÀ-ÿ0-9]+)*$"
                )
        String> produtos
) {}