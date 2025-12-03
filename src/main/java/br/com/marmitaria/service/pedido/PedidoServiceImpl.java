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
        Long usuarioId = contexto.getAuthenticatedUser().getId();
        Pedido pedido = contexto.getPedidoValidator().validar(id, usuarioId);
        return contexto.getPedidoMapper().paraDTO(pedido);
    }

    @Override
    @Transactional
    public RespostaPedidoDTO concluirPedido(ConcluirPedidoDTO dto) {
        Long usuarioId = contexto.getAuthenticatedUser().getId();

        Endereco endereco = contexto.getEnderecoValidator().validar(dto.enderecoId(), usuarioId);
        Carrinho carrinho = contexto.getCarrinhoValidator().validarExistente(usuarioId);
        Pedido pedido = contexto.getPedidoFactory().criarPedido(carrinho, endereco, dto.formaPagamento());

        contexto.getPedidoRepository().save(pedido);
        contexto.getCarrinhoService().removerCarrinho(carrinho);

        return contexto.getPedidoMapper().paraDTO(pedido);
    }
}