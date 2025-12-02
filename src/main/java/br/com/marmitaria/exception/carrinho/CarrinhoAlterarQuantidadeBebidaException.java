package br.com.marmitaria.exception.carrinho;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CarrinhoAlterarQuantidadeBebidaException extends BusinessException {
    public CarrinhoAlterarQuantidadeBebidaException(String nome, Integer quantidade) {
        super(String.format("Quantidade %s permitida é %d.", nome, quantidade), HttpStatus.BAD_REQUEST);
    }
}
