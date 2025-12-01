package br.com.marmitaria.exception.auth;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AuthDadosInvalidosException extends BusinessException {
    public AuthDadosInvalidosException() {
        super("E-mail ou senha inválidos.", HttpStatus.BAD_REQUEST);
    }
}
