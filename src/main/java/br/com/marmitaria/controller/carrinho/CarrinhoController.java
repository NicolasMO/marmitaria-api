package br.com.marmitaria.controller.carrinho;

import br.com.marmitaria.dto.carrinho.AdicionarCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.AlterarQuantidadeCarrinhoItemDTO;
import br.com.marmitaria.dto.carrinho.RespostaCarrinhoDTO;
import br.com.marmitaria.service.carrinho.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<RespostaCarrinhoDTO> listarCarrinho() {
        RespostaCarrinhoDTO carrinho = carrinhoService.listarCarrinho();
        return ResponseEntity.status(HttpStatus.OK).body(carrinho);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/item")
    public ResponseEntity<RespostaCarrinhoDTO> adicionarItem(@Valid @RequestBody AdicionarCarrinhoItemDTO dto) {
        RespostaCarrinhoDTO carrinho = carrinhoService.adicionarItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinho);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/item/{itemId}/quantidade")
    public ResponseEntity<RespostaCarrinhoDTO> alterarQuantidade(
            @PathVariable Long itemId,
            @Valid @RequestBody AlterarQuantidadeCarrinhoItemDTO dto)
    {
        RespostaCarrinhoDTO carrinho = carrinhoService.alterarQuantidade(itemId, dto);
        return ResponseEntity.status(HttpStatus.OK).body(carrinho);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long itemId) {
        carrinhoService.removerItem(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/limpar")
    public ResponseEntity<Void> limparCarrinho() {
        carrinhoService.limparCarrinho();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
