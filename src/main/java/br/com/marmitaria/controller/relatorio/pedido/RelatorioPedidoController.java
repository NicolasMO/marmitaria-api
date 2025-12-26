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
        Page<RelatorioPedidoDTO> relatorio = service.gerarRelatorio(inicio, fim, paginacao);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<RelatorioPedidoDTO>> relatorioPedidosPorUsuario(@PathVariable Long usuarioId, Pageable paginacao) {
        Page<RelatorioPedidoDTO> relatorio = service.gerarRelatorioPorUsuario(usuarioId, paginacao);
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/usuario/me")
    public ResponseEntity<Page<RelatorioPedidoDTO>> relatorioPedidosDoUsuario(Pageable paginacao) {
        Page<RelatorioPedidoDTO> relatorio = service.gerarRelatorioDoUsuario(paginacao);
        return ResponseEntity.ok(relatorio);
    }
 }
