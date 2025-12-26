package br.com.marmitaria.controller.relatorio.pedido;

import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import br.com.marmitaria.service.relatorio.pedido.RelatorioPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("relatorios/pedidos")
@RequiredArgsConstructor
public class RelatorioPedidoController {

    private final RelatorioPedidoService service;

    @GetMapping("/geral")
    public ResponseEntity<Page<RelatorioPedidoDTO>> gerarRelatorio(@RequestParam LocalDate inicio, @RequestParam LocalDate fim, Pageable paginacao) {
        return ResponseEntity.ok(service.gerarRelatorio(inicio, fim, paginacao));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<RelatorioPedidoDTO>> relatorioPedidoPorUsuario(@PathVariable Long id, Pageable paginacao) {

    }
 }
