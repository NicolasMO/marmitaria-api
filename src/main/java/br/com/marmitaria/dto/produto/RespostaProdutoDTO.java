package br.com.marmitaria.dto.produto;

import br.com.marmitaria.enums.TipoProduto;

import java.math.BigDecimal;

public record RespostaProdutoDTO(
        Long id,
        String nome,
        BigDecimal preco_unitario,
        String imagem,
        TipoProduto tipo
) {}
