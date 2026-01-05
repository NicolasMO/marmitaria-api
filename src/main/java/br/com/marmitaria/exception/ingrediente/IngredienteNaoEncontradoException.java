package br.com.marmitaria.exception.ingrediente;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class IngredienteNaoEncontradoException extends BusinessException {
    public IngredienteNaoEncontradoException(Long id) {
        super(String.format("Ingrediente com ID %d não encontrado.", id), HttpStatus.NOT_FOUND);
    }

    public IngredienteNaoEncontradoException(List<String> nomes) {
        super("Ingredientes não encontrados: " + String.join(", ", nomes), HttpStatus.NOT_FOUND);
    }
}
