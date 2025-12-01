package br.com.marmitaria.service.ingrediente;

import br.com.marmitaria.repository.ingrediente.IngredienteRepository;
import br.com.marmitaria.service.ingrediente.factory.IngredienteFactory;
import br.com.marmitaria.service.ingrediente.mapper.IngredienteMapper;
import br.com.marmitaria.service.ingrediente.validator.IngredienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredienteContext {

    public final IngredienteRepository ingredienteRepository;
    public final IngredienteMapper ingredienteMapper;
    public final IngredienteValidator ingredienteValidator;
    public final IngredienteFactory ingredienteFactory;

}
