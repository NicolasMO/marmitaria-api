package br.com.marmitaria.exception.carrinho;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CarrinhoItemNaoEncontradoException extends BusinessException {
    public CarrinhoItemNaoEncontradoException() {
        super("Item não encontrado.", HttpStatus.NOT_FOUND);
    }
}
