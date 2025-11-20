package br.com.marmitaria.service.ingrediente;

import java.util.List;

import br.com.marmitaria.dto.ingrediente.CadastroIngredienteDTO;
import br.com.marmitaria.dto.ingrediente.RespostaIngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;

public interface IngredienteService  {
    Ingrediente cadastrarIngrediente(CadastroIngredienteDTO dto);
    RespostaIngredienteDTO listarIngredientePorId(long id);
    List<RespostaIngredienteDTO> listarTodos();
    RespostaIngredienteDTO atualizarIngrediente(Long id, CadastroIngredienteDTO dto);
    void removerIngrediente(Long id);
}
