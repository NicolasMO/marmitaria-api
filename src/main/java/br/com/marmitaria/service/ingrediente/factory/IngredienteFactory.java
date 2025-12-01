package br.com.marmitaria.service.ingrediente.factory;

import br.com.marmitaria.dto.ingrediente.CadastroIngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import org.springframework.stereotype.Component;

@Component
public class IngredienteFactory {

    public Ingrediente criarIngrediente(CadastroIngredienteDTO dto) {
        return new Ingrediente(
                dto.nome(),
                dto.categoria()
        );
    }
}
