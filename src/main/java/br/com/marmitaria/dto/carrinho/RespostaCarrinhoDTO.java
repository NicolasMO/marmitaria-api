package br.com.marmitaria.dto.carrinho;

import java.util.List;

public record RespostaCarrinhoDTO(
        Long id,
        Long usuarioId,
        List<RespostaCarrinhoItemDTO> itens
) {}