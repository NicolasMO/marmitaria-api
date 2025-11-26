package br.com.marmitaria.service.ingrediente;

import java.util.List;

import br.com.marmitaria.dto.ingrediente.AtualizarIngredienteDTO;
import br.com.marmitaria.dto.ingrediente.RespostaIngredienteDTO;
import br.com.marmitaria.exception.RequisicaoVaziaException;
import br.com.marmitaria.exception.ingrediente.IngredienteJaExistenteException;
import br.com.marmitaria.exception.ingrediente.IngredienteNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marmitaria.dto.ingrediente.CadastroIngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.repository.ingrediente.IngredienteRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IngredienteServiceImpl implements IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Override
    @Transactional
    public Ingrediente cadastrarIngrediente(CadastroIngredienteDTO dto) {
        if (ingredienteRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new IngredienteJaExistenteException(dto.nome());
        }

        Ingrediente ingrediente = new Ingrediente(
          dto.nome(),
          dto.categoria()
        );

        return ingredienteRepository.save(ingrediente);
    }

    @Override
    public RespostaIngredienteDTO listarIngredientePorId(long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new IngredienteNaoEncontradoException(id));

        return new RespostaIngredienteDTO(
                ingrediente.getId(),
                ingrediente.getNome(),
                ingrediente.getCategoria()
        );
    }

	@Override
	public List<RespostaIngredienteDTO> listarTodos() {
		List<Ingrediente> ingredientes = ingredienteRepository.findAll();

		return ingredientes.stream().map(ing -> new RespostaIngredienteDTO(
                ing.getId(), ing.getNome(), ing.getCategoria()
        )).toList();
	}

    @Override
    @Transactional
    public RespostaIngredienteDTO atualizarIngrediente(Long id, AtualizarIngredienteDTO dto) {
        boolean nenhumCampoEnviado =
                (dto.nome() == null || dto.nome().isBlank()) &&
                        dto.categoria() == null;

        if (nenhumCampoEnviado) {
            throw new RequisicaoVaziaException("Envie ao menos um campo para atualização.");
        }

        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new IngredienteNaoEncontradoException(id));

        if (dto.nome() != null && !dto.nome().isBlank()) {
            boolean jaExiste = ingredienteRepository.existsByNomeIgnoreCase(dto.nome());
            if (jaExiste && !ingrediente.getNome().equalsIgnoreCase(dto.nome())) {
                throw new IngredienteJaExistenteException(dto.nome());
            }

            ingrediente.setNome(dto.nome());
        }

        if (dto.categoria() != null) {
            ingrediente.setCategoria(dto.categoria());
        }

        return new RespostaIngredienteDTO(
                ingrediente.getId(),
                ingrediente.getNome(),
                ingrediente.getCategoria()
        );
    }

    @Override
    @Transactional
    public void removerIngrediente(Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new IngredienteNaoEncontradoException(id));

        ingredienteRepository.delete(ingrediente);
    }


}
