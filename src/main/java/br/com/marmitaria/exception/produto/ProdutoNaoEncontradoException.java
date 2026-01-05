package br.com.marmitaria.exception.produto;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ProdutoNaoEncontradoException extends BusinessException {
    public ProdutoNaoEncontradoException(Long id) {
        super(String.format("Produto com ID %d não encontrado", id), HttpStatus.NOT_FOUND);
    }

    public ProdutoNaoEncontradoException(String nome) {
        super(String.format("Produto '%s' não encontrado", nome), HttpStatus.NOT_FOUND);
    }

    public ProdutoNaoEncontradoException(List<String> nomes) {
        super("Produtos não encontrados: " + String.join(", ", nomes), HttpStatus.NOT_FOUND);
    }
}
