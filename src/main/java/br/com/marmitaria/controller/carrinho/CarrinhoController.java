package br.com.marmitaria.controller.carrinho;

import br.com.marmitaria.dto.carrinho.AdicionarCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.AlterarQuantidadeCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.RespostaCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.RespostaCarrinhoItemDTO;
import br.com.marmitaria.entity.usuario.Response;
import br.com.marmitaria.service.carrinho.CarrinhoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<RespostaCarrinhoDTO> listarCarrinho() {
        RespostaCarrinhoDTO carrinho = carrinhoService.listarCarrinho();
        return ResponseEntity.status(HttpStatus.OK).body(carrinho);
    }

    @PostMapping("/item")
    public ResponseEntity<RespostaCarrinhoDTO> adicionarItem(@Valid @RequestBody AdicionarCarrinhoItemDTO dto) {
        RespostaCarrinhoDTO carrinho = carrinhoService.adicionarItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinho);
    }

    @PutMapping("/item/{itemId}/quantidade")
    public ResponseEntity<RespostaCarrinhoDTO> alterarQuantidade(
            @PathVariable Long itemId,
            @Valid @RequestBody AlterarQuantidadeCarrinhoItemDTO dto)
    {
        RespostaCarrinhoDTO carrinho = carrinhoService.alterarQuantidade(itemId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(carrinho);
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long itemId) {
        carrinhoService.removerItem(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/limpar")
    public ResponseEntity<Void> limparCarrinho() {
        carrinhoService.limparCarrinho();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
