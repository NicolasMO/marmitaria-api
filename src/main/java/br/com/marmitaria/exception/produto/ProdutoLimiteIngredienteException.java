package br.com.marmitaria.exception.produto;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ProdutoLimiteIngredienteException extends BusinessException {
    public ProdutoLimiteIngredienteException(String nome) {
        super(String.format("Quantidade inválida de %s.", nome), HttpStatus.BAD_REQUEST);
    }
}
