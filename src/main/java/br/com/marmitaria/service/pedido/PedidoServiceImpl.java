package br.com.marmitaria.service.pedido;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.pedido.ConcluirPedidoDTO;
import br.com.marmitaria.dto.pedido.RespostaPedidoDTO;
import br.com.marmitaria.dto.pedido.RespostaPedidoItemDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.CarrinhoItem;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.pedido.Pedido;
import br.com.marmitaria.entity.pedido.PedidoItem;
import br.com.marmitaria.exception.carrinho.CarrinhoNaoEncontradoException;
import br.com.marmitaria.exception.carrinho.CarrinhoVazioException;
import br.com.marmitaria.exception.endereco.EnderecoNaoEncontradoException;
import br.com.marmitaria.exception.endereco.EnderecoSemPermissaoException;
import br.com.marmitaria.exception.pedido.PedidoNaoEncontradoException;
import br.com.marmitaria.exception.pedido.PedidoSemPermissaoException;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.endereco.EnderecoRepository;
import br.com.marmitaria.repository.pedido.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    AuthenticatedUser authenticatedUser;

    @Autowired
    CarrinhoRepository carrinhoRespository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Override
    public RespostaPedidoDTO buscarPorId(Long id) {
        Long usuarioId = authenticatedUser.getId();

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        if (!pedido.getUsuario().getId().equals(usuarioId)) {
            throw new PedidoSemPermissaoException();
        }

        return mapToDTO(pedido);
    }

    @Override
    @Transactional
    public RespostaPedidoDTO concluirPedido(ConcluirPedidoDTO dto) {

        Long usuarioId = authenticatedUser.getId();

        Endereco endereco = enderecoRepository.findById(dto.enderecoId())
                .orElseThrow(() -> new EnderecoNaoEncontradoException(dto.enderecoId()));

        if (!endereco.getUsuario().getId().equals(usuarioId)) {
            throw new EnderecoSemPermissaoException();
        }

        Carrinho carrinho = carrinhoRespository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException());

        if (carrinho.getItens().isEmpty()) {
            throw new CarrinhoVazioException();
        }

        Pedido pedido = new Pedido(
                carrinho.getUsuario(),
                endereco.getLogradouro() + ", " + endereco.getNumero(),
                dto.formaPagamento()
                );

        for (CarrinhoItem item : carrinho.getItens()) {

            PedidoItem pedidoItem = new PedidoItem(
                    pedido,
                    item.getProduto(),
                    item.getQuantidade(),
                    item.getObservacao(),
                    item.getIngredientes()
            );

            pedido.getItens().add(pedidoItem);
        }

        BigDecimal total = carrinhoRespository.calcularTotais(carrinho.getId()).getValorTotal();
        pedido.setTotal(total);

        carrinhoRespository.delete(carrinho);
        pedidoRepository.save(pedido);

        return mapToDTO(pedido);
    }

    private RespostaPedidoDTO mapToDTO(Pedido pedido) {
        List<RespostaPedidoItemDTO> itens = pedido.getItens().stream().map(i -> new RespostaPedidoItemDTO(
                i.getId(),
                i.getProduto().getNome(),
                i.getProduto().getPrecoUnitario(),
                i.getQuantidade(),
                i.getObservacao(),
                i.getIngredientes().stream()
                        .map(Ingrediente::getNome)
                        .collect(Collectors.toSet())
        )).toList();

        return new RespostaPedidoDTO(
                pedido.getId(),
                pedido.getUsuario().getId(),
                pedido.getTotal(),
                pedido.getEnderecoEntrega(),
                pedido.getStatus(),
                pedido.getFormaPagamento(),
                itens,
                pedido.getDataPedido()
        );
    }
}
