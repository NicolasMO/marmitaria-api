package br.com.marmitaria.exception.endereco;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class EnderecoLimiteCadastradoException extends BusinessException {
    public EnderecoLimiteCadastradoException() {
        super("Limite de 3 endereços cadastrados.", HttpStatus.CONFLICT);
    }
}
