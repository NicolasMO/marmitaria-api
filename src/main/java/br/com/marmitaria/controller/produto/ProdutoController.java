package br.com.marmitaria.controller.produto;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.repository.produto.ProdutoRepository;

@RestController
@RequestMapping("produtos")
public class ProdutoController {
	private final ProdutoRepository produtoRepository;

	public ProdutoController(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<Produto>> listarProdutos() {
		List<Produto> produtos = produtoRepository.findAll();
		return ResponseEntity.ok(produtos);
	}
}
