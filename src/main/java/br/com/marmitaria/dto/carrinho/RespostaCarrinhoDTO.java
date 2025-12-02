package br.com.marmitaria.dto.carrinho;

import br.com.marmitaria.dto.item.RespostaItemDTO;

import java.math.BigDecimal;
import java.util.List;

public record RespostaCarrinhoDTO(
        Long id,
        String usuarioNome,
        List<RespostaItemDTO> itens,
        int totalProdutos,
        BigDecimal valorTotal
) {}