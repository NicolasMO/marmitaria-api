package br.com.marmitaria.service.relatorio.pedido;

import br.com.marmitaria.dto.pedido.RelatorioPedidoDTO;
import br.com.marmitaria.entity.pedido.Pedido;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.pedido.PedidoRepository;
import br.com.marmitaria.service.relatorio.pedido.mapper.RelatorioPedidoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RelatorioPedidoServiceImpl implements RelatorioPedidoService {

    private final RelatorioPedidoContext contexto;

    @Override
    public Page<RelatorioPedidoDTO> gerarRelatorio(LocalDate inicio, LocalDate fim, Pageable paginacao) {
        LocalDateTime inicioDia = inicio.atStartOfDay();
        LocalDateTime fimDia = fim.atTime(23, 59, 59);
        Page<Pedido> pedidos = contexto.getPedidoRepository().findByDataPedidoBetween(inicioDia, fimDia, paginacao);

        return pedidos.map(contexto.getRelatorioMapper()::paraDTO);
    }

    @Override
    public Page<RelatorioPedidoDTO> gerarRelatorioPorUsuario(Long usuarioId, Pageable paginacao) {
        Long usuarioAutenticadoId = contexto.getAuthenticatedUser().getId();

        Usuario usuario = contexto.getUsuarioValidator().validar(usuarioAutenticadoId);

        Page<Pedido> pedidos = contexto.getPedidoRepository().findByUsuarioId(usuarioId, paginacao);

        return pedidos.map(contexto.getRelatorioMapper()::paraDTO);
    }

    @Override
    public Page<RelatorioPedidoDTO> gerarRelatorioDoUsuario(Pageable paginacao) {
        Long usuarioAutenticadoId = contexto.getAuthenticatedUser().getId();

        Usuario usuario = contexto.getUsuarioValidator().validar(usuarioAutenticadoId);

        Page<Pedido> pedidos = contexto.getPedidoRepository().findByUsuarioId(usuarioAutenticadoId, paginacao);

        return pedidos.map(contexto.getRelatorioMapper()::paraDTO);
    }

}
