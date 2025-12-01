package br.com.marmitaria.exception.auth;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AuthCPFJaCadastradoException extends BusinessException {
    public AuthCPFJaCadastradoException() {
        super("CPF já cadastrado.", HttpStatus.CONFLICT);
    }
}
