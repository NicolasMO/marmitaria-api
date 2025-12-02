package br.com.marmitaria.exception.ingrediente;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class IngredienteListaNaoEncontradaException extends BusinessException {
    public IngredienteListaNaoEncontradaException() {
        super("Um ou mais ingredientes informados não existem ou estão repetidos", HttpStatus.BAD_REQUEST);
    }
}
