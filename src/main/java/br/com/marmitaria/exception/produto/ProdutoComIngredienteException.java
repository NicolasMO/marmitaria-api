package br.com.marmitaria.exception.produto;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ProdutoComIngredienteException extends BusinessException {
    public ProdutoComIngredienteException(String message) {
            super(message, HttpStatus.BAD_REQUEST);
    }
}
