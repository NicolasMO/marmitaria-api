package br.com.marmitaria.dto.carrinho;

import java.math.BigDecimal;
import java.util.List;

public record RespostaCarrinhoDTO(
        Long id,
        Long usuarioId,
        List<RespostaCarrinhoItemDTO> itens,
        int totalProdutos,
        BigDecimal valorTotal
) {}