package br.com.marmitaria.controller.carrinho;

import br.com.marmitaria.dto.carrinho.AdicionarCarrinhoItemDTO;
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

    @GetMapping("/{usuarioId}")
    public ResponseEntity<RespostaCarrinhoDTO> listarCarrinho(@PathVariable Long usuarioId) {
        RespostaCarrinhoDTO carrinho = carrinhoService.listarCarrinho(usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(carrinho);
    }

    @PostMapping("/{usuarioId}/item")
    public ResponseEntity<RespostaCarrinhoDTO> adicionarItem(@PathVariable Long usuarioId, @Valid @RequestBody AdicionarCarrinhoItemDTO dto) {
        RespostaCarrinhoDTO carrinho = carrinhoService.adicionarItem(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinho);
    }
}
