package br.com.marmitaria.exception.carrinho;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CarrinhoUsuarioSemPermissaoException extends BusinessException {
    public CarrinhoUsuarioSemPermissaoException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
