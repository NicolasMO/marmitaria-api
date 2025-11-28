package br.com.marmitaria.exception.carrinho;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CarrinhoVazioException extends BusinessException {
    public CarrinhoVazioException() {
        super("Carrinho Vazio.", HttpStatus.BAD_REQUEST);
    }
}
