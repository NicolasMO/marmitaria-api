package br.com.marmitaria.dto.pedido;

import br.com.marmitaria.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;

public record ConcluirPedidoDTO(
        @NotNull(message = "Endereço de entrega é obrigatório.")
        Long enderecoId,

        @NotNull(message = "Forma de pagamento é obrigatória.")
        FormaPagamento formaPagamento
) {}