package br.com.marmitaria.exception.carrinho;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CarrinhoAlterarQuantidadeMarmitaException extends BusinessException {
    public CarrinhoAlterarQuantidadeMarmitaException() {
        super("Não é permitido alterar a quantidade de marmitas", HttpStatus.BAD_REQUEST);
    }
}
