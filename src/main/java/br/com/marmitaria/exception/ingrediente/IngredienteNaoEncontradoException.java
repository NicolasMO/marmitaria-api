package br.com.marmitaria.exception.ingrediente;

import br.com.marmitaria.exception.BusinessException;

public class IngredienteNaoEncontradoException extends BusinessException {
    public IngredienteNaoEncontradoException(Long id) {
        super(String.format("Ingrediente com ID %d não encontrado", id));
    }
}
