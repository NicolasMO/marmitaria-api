package br.com.marmitaria.controller.carrinho;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.dto.carrinho.AddCarrinhoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.service.carrinho.CarrinhoService;

@RestController
@RequestMapping("carrinho")
public class CarrinhoController {
	private final CarrinhoService carrinhoService;
	
	public CarrinhoController(CarrinhoService carrinhoService) {
		this.carrinhoService = carrinhoService;
	}
	
	@PostMapping("/adicionar")
	public ResponseEntity<Carrinho> adicionarItem(@RequestParam AddCarrinhoDTO addCarrinho) {
		Carrinho carrinho = carrinhoService.adicionarItemCarrinho(addCarrinho);
		return ResponseEntity.ok(carrinho);
	}
}
