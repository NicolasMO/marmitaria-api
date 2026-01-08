package br.com.marmitaria.controller.relatorio.pedido;

import br.com.marmitaria.dto.pedido.RelatorioIngredienteFiltroDTO;
import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import br.com.marmitaria.dto.pedido.RelatorioProdutoFiltroDTO;
import br.com.marmitaria.service.relatorio.pedido.RelatorioPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("relatorios/pedidos")
@RequiredArgsConstructor
public class RelatorioPedidoController {

    private final RelatorioPedidoService service;

    @PreAuthorize("hasAnyRoles('ADMIN', 'OPERADOR')")
    @GetMapping("/geral")
    public ResponseEntity<Page<RelatorioPedidoDTO>> gerarRelatorio(@RequestParam LocalDate inicio, @RequestParam LocalDate fim, Pageable paginacao) {
        Page<RelatorioPedidoDTO> relatorio = service.gerarRelatorio(inicio, fim, paginacao);
        return ResponseEntity.ok(relatorio);
    }

    @PreAuthorize("hasAnyRoles('ADMIN', 'OPERADOR')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<RelatorioPedidoDTO>> relatorioPedidosPorUsuario(@PathVariable Long usuarioId, Pageable paginacao) {
        Page<RelatorioPedidoDTO> relatorio = service.gerarRelatorioPorUsuario(usuarioId, paginacao);
        return ResponseEntity.ok(relatorio);
    }

    @PreAuthorize("hasAnyRoles('ADMIN', 'OPERADOR')")
    @GetMapping("/usuario/me")
    public ResponseEntity<Page<RelatorioPedidoDTO>> relatorioPedidosDoUsuario(Pageable paginacao) {
        Page<RelatorioPedidoDTO> relatorio = service.gerarRelatorioDoUsuario(paginacao);
        return ResponseEntity.ok(relatorio);
    }

    @PreAuthorize("hasAnyRoles('ADMIN', 'OPERADOR')")
    @GetMapping("/produtos")
    public ResponseEntity<Page<RelatorioPedidoDTO>> relatorioPorProdutos(@Valid RelatorioProdutoFiltroDTO filtro, Pageable paginacao) {
        Page<RelatorioPedidoDTO> relatorio = service.gerarRelatorioPorProdutos(filtro.produtos(), paginacao);
        return ResponseEntity.ok(relatorio);
    }

    @PreAuthorize("hasAnyRoles('ADMIN', 'OPERADOR')")
    @GetMapping("/ingredientes")
    public ResponseEntity<Page<RelatorioPedidoDTO>> relatorioPorIngredientes(@Valid RelatorioIngredienteFiltroDTO filtro, Pageable paginacao) {
        Page<RelatorioPedidoDTO> relatorio = service.gerarRelatorioPorIngredientes(filtro.ingrediente(), paginacao);

        return ResponseEntity.ok(relatorio);
    }
 }
