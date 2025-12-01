package br.com.marmitaria.exception.usuario;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UsuarioNaoEncontradoException extends BusinessException {
    public UsuarioNaoEncontradoException() {
        super("Usuário não encontrado.", HttpStatus.NOT_FOUND);
    }
}
