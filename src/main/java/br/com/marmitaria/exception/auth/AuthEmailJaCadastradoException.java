package br.com.marmitaria.exception.auth;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AuthEmailJaCadastradoException extends BusinessException {
    public AuthEmailJaCadastradoException() {
        super("E-mail já cadastrado.", HttpStatus.CONFLICT);
    }
}
