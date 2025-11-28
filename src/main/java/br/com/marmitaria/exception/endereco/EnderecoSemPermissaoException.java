package br.com.marmitaria.exception.endereco;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class EnderecoSemPermissaoException extends BusinessException {
    public EnderecoSemPermissaoException() {
        super("Você não tem permissão para usar esse endereço.", HttpStatus.FORBIDDEN);
    }
}
