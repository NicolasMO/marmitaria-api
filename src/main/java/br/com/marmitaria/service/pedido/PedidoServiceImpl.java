package br.com.marmitaria.service.pedido;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.item.RespostaItemDTO;
import br.com.marmitaria.dto.pedido.ConcluirPedidoDTO;
import br.com.marmitaria.dto.pedido.RespostaPedidoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.CarrinhoItem;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.pedido.Pedido;
import br.com.marmitaria.entity.pedido.PedidoItem;
import br.com.marmitaria.exception.pedido.PedidoNaoEncontradoException;
import br.com.marmitaria.exception.pedido.PedidoSemPermissaoException;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.pedido.PedidoRepository;
import br.com.marmitaria.service.pedido.factory.PedidoFactory;
import br.com.marmitaria.service.pedido.mapper.PedidoMapper;
import br.com.marmitaria.service.pedido.validator.CarrinhoValidator;
import br.com.marmitaria.service.pedido.validator.EnderecoValidator;
import br.com.marmitaria.service.pedido.validator.PedidoValidator;
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
    CarrinhoRepository carrinhoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    EnderecoValidator enderecoValidator;

    @Autowired
    CarrinhoValidator carrinhoValidator;

    @Autowired
    PedidoValidator pedidoValidator;

    @Autowired
    PedidoFactory pedidoFactory;

    @Autowired
    PedidoMapper pedidoMapper;

    @Override
    public RespostaPedidoDTO buscarPorId(Long id) {
        Long usuarioId = authenticatedUser.getId();

        Pedido pedido = pedidoValidator.validar(id, usuarioId);

        return pedidoMapper.paraDTO(pedido);
    }

    @Override
    @Transactional
    public RespostaPedidoDTO concluirPedido(ConcluirPedidoDTO dto) {

        Long usuarioId = authenticatedUser.getId();

        Endereco endereco = enderecoValidator.validar(dto.enderecoId(), usuarioId);

        Carrinho carrinho = carrinhoValidator.validar(usuarioId);

        Pedido pedido = pedidoFactory.criarPedido(carrinho, endereco, dto.formaPagamento());

        pedidoRepository.save(pedido);
        carrinhoRepository.delete(carrinho);

        return pedidoMapper.paraDTO(pedido);
    }
}
