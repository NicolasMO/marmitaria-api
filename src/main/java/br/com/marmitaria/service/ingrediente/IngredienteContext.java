package br.com.marmitaria.service.ingrediente;

import br.com.marmitaria.repository.ingrediente.IngredienteRepository;
import br.com.marmitaria.service.ingrediente.factory.IngredienteFactory;
import br.com.marmitaria.service.ingrediente.mapper.IngredienteMapper;
import br.com.marmitaria.service.ingrediente.validator.IngredienteValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class IngredienteContext {

    private final IngredienteRepository ingredienteRepository;
    private final IngredienteMapper ingredienteMapper;
    private final IngredienteValidator ingredienteValidator;
    private final IngredienteFactory ingredienteFactory;

}
