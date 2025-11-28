package br.com.marmitaria.exception.pedido;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PedidoNaoEncontradoException extends BusinessException {
    public PedidoNaoEncontradoException(Long id) {
        super(String.format("Pedido com ID %d não encontrado.", id), HttpStatus.NOT_FOUND);
    }
}
