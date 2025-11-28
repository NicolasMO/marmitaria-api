package br.com.marmitaria.exception;

import org.springframework.http.HttpStatus;

public class RequisicaoVaziaException extends BusinessException {
    public RequisicaoVaziaException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
