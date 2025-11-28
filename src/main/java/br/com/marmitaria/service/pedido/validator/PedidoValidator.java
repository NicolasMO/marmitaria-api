package br.com.marmitaria.service.pedido.validator;

import br.com.marmitaria.entity.pedido.Pedido;
import br.com.marmitaria.exception.pedido.PedidoNaoEncontradoException;
import br.com.marmitaria.exception.pedido.PedidoSemPermissaoException;
import br.com.marmitaria.repository.pedido.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoValidator {

    @Autowired
    PedidoRepository pedidoRepository;

    public Pedido validar(Long id, Long usuarioId) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        if (!pedido.getUsuario().getId().equals(usuarioId)) {
            throw new PedidoSemPermissaoException();
        }

        return pedido;
    }
}
