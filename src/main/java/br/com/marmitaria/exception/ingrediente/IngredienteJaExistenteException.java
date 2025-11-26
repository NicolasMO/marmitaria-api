package br.com.marmitaria.exception.ingrediente;

import br.com.marmitaria.exception.BusinessException;

public class IngredienteJaExistenteException extends BusinessException {
    public IngredienteJaExistenteException(String nome) {
        super(String.format("Ingrediente '%s' já cadastrado.", nome));
    }
}
