package br.com.marmitaria.exception.produto;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ProdutoJaExistenteException extends BusinessException {
    public ProdutoJaExistenteException(String nome) {
        super(String.format("Produto já existente: '%s'.", nome), HttpStatus.CONFLICT);
    }
}
