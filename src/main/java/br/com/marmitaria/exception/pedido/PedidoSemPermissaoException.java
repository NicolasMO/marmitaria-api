package br.com.marmitaria.exception.pedido;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class PedidoSemPermissaoException extends BusinessException {
    public PedidoSemPermissaoException() {
        super("Você não tem permissão para visualizar este pedido.", HttpStatus.FORBIDDEN);
    }
}
