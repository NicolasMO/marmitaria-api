package br.com.marmitaria.exception;

import org.springframework.http.HttpStatus;

public class RequisicaoVaziaException extends BusinessException {
    public RequisicaoVaziaException() {
        super("Requisição vazia enviada.", HttpStatus.BAD_REQUEST);
    }
}
