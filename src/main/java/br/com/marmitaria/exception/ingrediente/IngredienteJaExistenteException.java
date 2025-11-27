package br.com.marmitaria.exception.ingrediente;

import br.com.marmitaria.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class IngredienteJaExistenteException extends BusinessException {
    public IngredienteJaExistenteException(String nome) {
        super(String.format("Ingrediente já existente: '%s'.", nome), HttpStatus.CONFLICT);
    }
}
