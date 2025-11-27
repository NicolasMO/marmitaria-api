package br.com.marmitaria.dto.pedido;

import br.com.marmitaria.enums.FormaPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConcluirPedidoDTO(
        @NotBlank(message = "Endereço de entrega é obrigatório.")
        String enderecoEntrega,

        @NotNull(message = "Forma de pagamento é obrigatória.")
        FormaPagamento formaPagamento
) {}