package br.com.marmitaria.controller.produto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.repository.produto.IngredienteRepository;

@RestController
@RequestMapping("ingredientes")
public class IngredienteController {
	private final IngredienteRepository ingredienteRepository;

	public IngredienteController(IngredienteRepository ingredienteRepository) {
		this.ingredienteRepository = ingredienteRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<Ingrediente>> listarIngredientes() {
		List<Ingrediente> ingredientes= ingredienteRepository.findAll();
		return ResponseEntity.ok(ingredientes);
	}
}
