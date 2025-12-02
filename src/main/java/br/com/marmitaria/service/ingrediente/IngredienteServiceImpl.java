package br.com.marmitaria.service.ingrediente;

import java.util.List;

import br.com.marmitaria.dto.ingrediente.AtualizarIngredienteDTO;
import br.com.marmitaria.dto.ingrediente.RespostaIngredienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.marmitaria.dto.ingrediente.CadastroIngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredienteServiceImpl implements IngredienteService {

    private final IngredienteContext contexto;

    @Override
    @Transactional
    public Ingrediente cadastrarIngrediente(CadastroIngredienteDTO dto) {
        contexto.getIngredienteValidator().validarSeNomeExiste(dto.nome());
        Ingrediente ingrediente = contexto.getIngredienteFactory().criarIngrediente(dto);
        return contexto.getIngredienteRepository().save(ingrediente);
    }

    @Override
    public RespostaIngredienteDTO listarIngredientePorId(long id) {
        Ingrediente ingrediente = contexto.getIngredienteValidator().validar(id);
        return contexto.getIngredienteMapper().paraDTO(ingrediente);
    }

	@Override
	public List<RespostaIngredienteDTO> listarTodos() {
		List<Ingrediente> ingredientes = contexto.getIngredienteRepository().findAll();
		return contexto.getIngredienteMapper().paraListaDTO(ingredientes);
	}

    @Override
    @Transactional
    public RespostaIngredienteDTO atualizarIngrediente(Long id, AtualizarIngredienteDTO dto) {
        contexto.getIngredienteValidator().validarRequisicaoVazia(dto);

        Ingrediente ingrediente = contexto.getIngredienteValidator().validar(id);

        if (dto.nome() != null && !dto.nome().isBlank()) {
            contexto.getIngredienteValidator().validarSeNomeExiste(dto.nome());
            ingrediente.setNome(dto.nome());
        }

        if (dto.categoria() != null) {
            ingrediente.setCategoria(dto.categoria());
        }

        return contexto.getIngredienteMapper().paraDTO(ingrediente);
    }

    @Override
    @Transactional
    public void removerIngrediente(Long id) {
        Ingrediente ingrediente = contexto.getIngredienteValidator().validar(id);
        contexto.getIngredienteRepository().delete(ingrediente);
    }
}
