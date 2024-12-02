package br.com.marmitaria.service.produto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.marmitaria.dto.produto.IngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.repository.produto.IngredienteRepository;

@Service
public class IngredienteServiceImpl implements IngredienteService {
	private final IngredienteRepository ingredienteRepository;
	
	public IngredienteServiceImpl(IngredienteRepository ingredienteRepository) {
		this.ingredienteRepository = ingredienteRepository;
	}
	
	@Override
	public List<IngredienteDTO> listarIngredientes() {
		List<Ingrediente> ingredientes = ingredienteRepository.findAll();
		
		return ingredientes.stream().map(ing -> new IngredienteDTO(ing.getId(), ing.getNome(), ing.getCategoria()))
					.collect(Collectors.toList());
	}
}
