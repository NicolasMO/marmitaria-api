package br.com.marmitaria.exception.usuario;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UsuarioNaoConfirmadoException extends BusinessException {
    public UsuarioNaoConfirmadoException() {
        super("Usuário não confirmado, verifique e-mail.", HttpStatus.FORBIDDEN);
    }
}
