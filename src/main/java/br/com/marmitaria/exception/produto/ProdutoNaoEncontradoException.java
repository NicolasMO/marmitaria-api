package br.com.marmitaria.exception.produto;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ProdutoNaoEncontradoException extends BusinessException {
    public ProdutoNaoEncontradoException(Long id) {
        super(String.format("Produto com ID %d não encontrado", id), HttpStatus.NOT_FOUND);
    }
}
