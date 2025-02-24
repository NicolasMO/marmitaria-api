package br.com.marmitaria.controller.carrinho;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.dto.carrinho.AdicionarItemCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.CarrinhoResponseDTO;
import br.com.marmitaria.service.carrinho.CarrinhoService;

@RestController
@RequestMapping("carrinho")
public class CarrinhoController {
	private final CarrinhoService carrinhoService;
	
	public CarrinhoController(CarrinhoService carrinhoService) {
		this.carrinhoService = carrinhoService;
	}
	
	@GetMapping("/{usuarioId}")
    public ResponseEntity<CarrinhoResponseDTO> listarItensDoCarrinho(@PathVariable Long usuarioId) {
			CarrinhoResponseDTO carrinho = carrinhoService.listarItensDoCarrinho(usuarioId);
            return ResponseEntity.ok(carrinho);
    }
	
	@PostMapping("/adicionar")
	public ResponseEntity<?> adicionarItemCarrinho(@RequestBody AdicionarItemCarrinhoDTO dto) {
            carrinhoService.adicionarItemAoCarrinho(dto);
            return ResponseEntity.ok("Item adicionado com sucesso");
    }
	
	@DeleteMapping("{carrinhoId}")
	public ResponseEntity<?> limparCarrinho(@PathVariable Long carrinhoId) {
		carrinhoService.limparCarrinho(carrinhoId);
		return ResponseEntity.ok("Carrinho limpo!");
	}
	
	@DeleteMapping("{carrinhoId}/{itemId}")
	public ResponseEntity<?> limparItemCarrinho(@PathVariable Long itemId, @PathVariable Long carrinhoId) {
		carrinhoService.removerItemDoCarrinho(itemId, carrinhoId);
		return ResponseEntity.ok("Item removido!");
	}
}
