package br.com.marmitaria.service.ingrediente;

import java.util.List;

import br.com.marmitaria.dto.ingrediente.RespostaIngredienteDTO;
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
        if (ingredienteRepository.existsByNomeIgnoreCaseAndCategoria(dto.nome(), dto.categoria())) {
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
    public RespostaIngredienteDTO atualizarIngrediente(Long id, CadastroIngredienteDTO dto) {
        if (ingredienteRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new IngredienteJaExistenteException(dto.nome());
        }

        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new IngredienteNaoEncontradoException(id));

        ingrediente.atualizarDados(dto.nome(), dto.categoria());
        ingredienteRepository.save(ingrediente);

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
