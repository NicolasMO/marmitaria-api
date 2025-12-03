package br.com.marmitaria.service.pedido;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.repository.pedido.PedidoRepository;
import br.com.marmitaria.service.carrinho.CarrinhoService;
import br.com.marmitaria.service.endereco.validator.EnderecoValidator;
import br.com.marmitaria.service.pedido.factory.PedidoFactory;
import br.com.marmitaria.service.pedido.mapper.PedidoMapper;
import br.com.marmitaria.service.carrinho.validator.CarrinhoValidator;
import br.com.marmitaria.service.pedido.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoContext {

    public final AuthenticatedUser authenticatedUser;
    public final PedidoRepository pedidoRepository;
    public final CarrinhoService carrinhoService;
    public final EnderecoValidator enderecoValidator;
    public final CarrinhoValidator carrinhoValidator;
    public final PedidoValidator pedidoValidator;
    public final PedidoFactory pedidoFactory;
    public final PedidoMapper pedidoMapper;
    
}
