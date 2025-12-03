package br.com.marmitaria.service.pedido;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.repository.pedido.PedidoRepository;
import br.com.marmitaria.service.carrinho.CarrinhoService;
import br.com.marmitaria.service.endereco.validator.EnderecoValidator;
import br.com.marmitaria.service.pedido.factory.PedidoFactory;
import br.com.marmitaria.service.pedido.mapper.PedidoMapper;
import br.com.marmitaria.service.carrinho.validator.CarrinhoValidator;
import br.com.marmitaria.service.pedido.validator.PedidoValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class PedidoContext {

    private final AuthenticatedUser authenticatedUser;
    private final PedidoRepository pedidoRepository;
    private final CarrinhoService carrinhoService;
    private final EnderecoValidator enderecoValidator;
    private final CarrinhoValidator carrinhoValidator;
    private final PedidoValidator pedidoValidator;
    private final PedidoFactory pedidoFactory;
    private final PedidoMapper pedidoMapper;
    
}
