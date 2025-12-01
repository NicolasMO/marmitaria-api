package br.com.marmitaria.service.ingrediente.mapper;

import br.com.marmitaria.dto.ingrediente.RespostaIngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IngredienteMapper {

    public RespostaIngredienteDTO paraDTO(Ingrediente ingrediente) {
        return new RespostaIngredienteDTO(
                ingrediente.getId(),
                ingrediente.getNome(),
                ingrediente.getCategoria()
        );
    }

    public List<RespostaIngredienteDTO> paraListaDTO(List<Ingrediente> ingredientes) {
        return ingredientes.stream().map(this::paraDTO).toList();
    }
}
