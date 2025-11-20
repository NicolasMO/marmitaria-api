package br.com.marmitaria.dto.produto;

import br.com.marmitaria.enums.TipoProduto;

public record RespostaProdutoDTO(
        Long id,
        String nome,
        Double preco,
        String imagem,
        TipoProduto tipo
) {}
