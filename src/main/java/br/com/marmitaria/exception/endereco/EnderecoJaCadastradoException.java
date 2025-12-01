package br.com.marmitaria.exception.endereco;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class EnderecoJaCadastradoException extends BusinessException {
    public EnderecoJaCadastradoException() {
        super("Endereço já cadastrado, informe outro.", HttpStatus.CONFLICT);
    }
}
