package br.com.marmitaria.controller.produto;

import java.util.List;

import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
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

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@Valid @RequestBody CadastroProdutoDTO dto) {
        Produto produto = produtoService.cadastrarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }
}
