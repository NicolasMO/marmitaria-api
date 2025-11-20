package br.com.marmitaria.controller.produto;

import java.util.List;

import br.com.marmitaria.dto.produto.AtualizarProdutoDTO;
import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
import br.com.marmitaria.dto.produto.RespostaProdutoDTO;
import br.com.marmitaria.service.produto.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.repository.produto.ProdutoRepository;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<RespostaProdutoDTO>> listarProdutos() {
        List<RespostaProdutoDTO> produtos = produtoService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@Valid @RequestBody CadastroProdutoDTO dto) {
        Produto produto = produtoService.cadastrarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PutMapping("{id}")
    public ResponseEntity<RespostaProdutoDTO> atualizarProduto(@PathVariable Long id, @Valid @RequestBody AtualizarProdutoDTO dto) {
        RespostaProdutoDTO produto = produtoService.atualizarProduto(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removerProduto(@PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Produto removido com sucesso.");
    }
}
