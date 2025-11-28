package br.com.marmitaria.service.pedido;

import br.com.marmitaria.dto.pedido.ConcluirPedidoDTO;
import br.com.marmitaria.dto.pedido.RespostaPedidoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.pedido.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoContext contexto;

    @Override
    public RespostaPedidoDTO buscarPorId(Long id) {
        Long usuarioId = getUsuarioIdAutenticado();

        Pedido pedido = contexto.pedidoValidator.validar(id, usuarioId);

        return contexto.pedidoMapper.paraDTO(pedido);
    }

    @Override
    @Transactional
    public RespostaPedidoDTO concluirPedido(ConcluirPedidoDTO dto) {
        Long usuarioId = getUsuarioIdAutenticado();

        Endereco endereco = contexto.enderecoValidator.validar(dto.enderecoId(), usuarioId);
        Carrinho carrinho = contexto.carrinhoValidator.validar(usuarioId);
        Pedido pedido = contexto.pedidoFactory.criarPedido(carrinho, endereco, dto.formaPagamento());

        contexto.pedidoRepository.save(pedido);
        contexto.carrinhoService.removerCarrinho(carrinho);

        return contexto.pedidoMapper.paraDTO(pedido);
    }

    private Long getUsuarioIdAutenticado() {
        Long usuarioId = contexto.authenticatedUser.getId();
        return usuarioId;
    }
}