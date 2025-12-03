package br.com.marmitaria.exception.produto;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ProdutoTipoInvalidoException extends BusinessException {
    public ProdutoTipoInvalidoException() {
        super("Tipo de Produto inválido", HttpStatus.BAD_REQUEST);
    }
}
