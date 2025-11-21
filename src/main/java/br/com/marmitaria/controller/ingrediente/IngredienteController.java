package br.com.marmitaria.controller.ingrediente;

import java.util.List;

import br.com.marmitaria.dto.ingrediente.CadastroIngredienteDTO;
import br.com.marmitaria.dto.ingrediente.RespostaIngredienteDTO;
import br.com.marmitaria.service.ingrediente.IngredienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.marmitaria.entity.ingrediente.Ingrediente;

@RestController
@RequestMapping("ingredientes")
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

	@GetMapping
	public ResponseEntity<List<RespostaIngredienteDTO>> listarIngredientes() {
		List<RespostaIngredienteDTO> ingredientes = ingredienteService.listarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(ingredientes);
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<RespostaIngredienteDTO> listarIngredientePorId(@PathVariable Long id) {
        RespostaIngredienteDTO ingrediente = ingredienteService.listarIngredientePorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(ingrediente);
    }

    @PostMapping
    public ResponseEntity<Ingrediente> cadastrarIngrediente(@Valid @RequestBody CadastroIngredienteDTO dto) {
        Ingrediente ingrediente = ingredienteService.cadastrarIngrediente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingrediente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespostaIngredienteDTO> atualizarIngrediente(@PathVariable Long id, @Valid @RequestBody CadastroIngredienteDTO dto) {
        RespostaIngredienteDTO ingrediente = ingredienteService.atualizarIngrediente(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(ingrediente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerIngrediente(@PathVariable Long id) {
        ingredienteService.removerIngrediente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
