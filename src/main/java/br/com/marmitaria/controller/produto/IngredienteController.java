package br.com.marmitaria.controller.produto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.dto.produto.QuantidadeIngredientesDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.enums.QuantidadeIngredientes;
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
	
	@GetMapping("/limites/{produtoId}")
	public ResponseEntity<?> limiteIngredientesPorMarmita(@PathVariable int produtoId) {
		try {
            QuantidadeIngredientes quantidade = QuantidadeIngredientes.fromProdutoId(produtoId);
            QuantidadeIngredientesDTO dto = new QuantidadeIngredientesDTO(quantidade);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	}
}
