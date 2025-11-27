package br.com.marmitaria.exception.carrinho;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CarrinhoNaoEncontradoException extends BusinessException {
    public CarrinhoNaoEncontradoException() {
        super("Carrinho não encontrado.", HttpStatus.NOT_FOUND);
    }
}
