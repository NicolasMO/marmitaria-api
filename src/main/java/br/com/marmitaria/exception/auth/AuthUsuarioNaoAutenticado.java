package br.com.marmitaria.exception.auth;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AuthUsuarioNaoAutenticado extends BusinessException {
    public AuthUsuarioNaoAutenticado() {
        super("Usuário não autenticado.", HttpStatus.UNAUTHORIZED);
    }
}
