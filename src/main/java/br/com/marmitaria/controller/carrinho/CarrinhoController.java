package br.com.marmitaria.controller.carrinho;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@PostMapping
	public ResponseEntity<Carrinho> adicionarItem(@RequestBody AddCarrinhoDTO addCarrinho) {
		Carrinho carrinho = carrinhoService.adicionarItemCarrinho(addCarrinho);
		return new ResponseEntity<>(carrinho, HttpStatus.CREATED);
	}
}
