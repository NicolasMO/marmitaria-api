package br.com.marmitaria.exception.endereco;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class EnderecoNaoEncontradoException extends BusinessException {
    public EnderecoNaoEncontradoException(Long id) {
        super(String.format("Endereço de ID %d não encontrado", id), HttpStatus.NOT_FOUND);
    }
}
