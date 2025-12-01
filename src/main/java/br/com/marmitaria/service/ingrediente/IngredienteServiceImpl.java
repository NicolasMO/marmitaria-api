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
        contexto.ingredienteValidator.validarSeNomeExiste(dto.nome());
        Ingrediente ingrediente = contexto.ingredienteFactory.criarIngrediente(dto);
        return contexto.ingredienteRepository.save(ingrediente);
    }

    @Override
    public RespostaIngredienteDTO listarIngredientePorId(long id) {
        Ingrediente ingrediente = contexto.ingredienteValidator.validar(id);
        return contexto.ingredienteMapper.paraDTO(ingrediente);
    }

	@Override
	public List<RespostaIngredienteDTO> listarTodos() {
		List<Ingrediente> ingredientes = contexto.ingredienteRepository.findAll();
		return contexto.ingredienteMapper.paraListaDTO(ingredientes);
	}

    @Override
    @Transactional
    public RespostaIngredienteDTO atualizarIngrediente(Long id, AtualizarIngredienteDTO dto) {
        contexto.ingredienteValidator.validarRequisicaoVazia(dto);

        Ingrediente ingrediente = contexto.ingredienteValidator.validar(id);

        if (dto.nome() != null && !dto.nome().isBlank()) {
            contexto.ingredienteValidator.validarSeNomeExiste(dto.nome());
            ingrediente.setNome(dto.nome());
        }

        if (dto.categoria() != null) {
            ingrediente.setCategoria(dto.categoria());
        }

        return contexto.ingredienteMapper.paraDTO(ingrediente);
    }

    @Override
    @Transactional
    public void removerIngrediente(Long id) {
        Ingrediente ingrediente = contexto.ingredienteValidator.validar(id);
        contexto.ingredienteRepository.delete(ingrediente);
    }
}
