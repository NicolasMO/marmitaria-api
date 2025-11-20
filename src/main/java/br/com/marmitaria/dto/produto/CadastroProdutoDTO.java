package br.com.marmitaria.dto.produto;

import br.com.marmitaria.enums.TipoProduto;
import jakarta.validation.constraints.*;

public record CadastroProdutoDTO(
        @NotBlank
        String nome,

        @NotNull
        @Positive
        Double preco_unitario,

        @NotBlank
        String imagem,

        @NotNull
        TipoProduto tipo
) {}
