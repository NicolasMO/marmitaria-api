package br.com.marmitaria.controller.pedido;

import br.com.marmitaria.dto.pedido.ConcluirPedidoDTO;
import br.com.marmitaria.dto.pedido.RespostaPedidoDTO;
import br.com.marmitaria.service.pedido.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<RespostaPedidoDTO> buscarPedidoPorID(@PathVariable Long id) {
        RespostaPedidoDTO dto = pedidoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/concluir")
    public ResponseEntity<RespostaPedidoDTO> concluirPedido(@Valid @RequestBody ConcluirPedidoDTO dto) {
        RespostaPedidoDTO pedido = pedidoService.concluirPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }
}
