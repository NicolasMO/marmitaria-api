package br.com.marmitaria.service.ingrediente;

import java.util.List;

import br.com.marmitaria.dto.ingrediente.AtualizarIngredienteDTO;
import br.com.marmitaria.dto.ingrediente.CadastroIngredienteDTO;
import br.com.marmitaria.dto.ingrediente.RespostaIngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;

public interface IngredienteService  {
    RespostaIngredienteDTO cadastrarIngrediente(CadastroIngredienteDTO dto);
    RespostaIngredienteDTO listarIngredientePorId(Long id);
    List<RespostaIngredienteDTO> listarTodos();
    RespostaIngredienteDTO atualizarIngrediente(Long id, AtualizarIngredienteDTO dto);
    void removerIngrediente(Long id);
}
