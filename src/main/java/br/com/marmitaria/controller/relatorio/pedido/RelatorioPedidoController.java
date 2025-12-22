package br.com.marmitaria.controller.relatorio.pedido;

import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import br.com.marmitaria.service.relatorio.pedido.RelatorioPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("relatorios/pedidos")
@RequiredArgsConstructor
public class RelatorioPedidoController {

    private final RelatorioPedidoService service;

    @GetMapping
    public ResponseEntity<List<RelatorioPedidoDTO>> gerarRelatorio(@RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fim) {
        return ResponseEntity.ok(service.gerarRelatorio(inicio, fim));
    }
}
