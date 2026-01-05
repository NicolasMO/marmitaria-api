package br.com.marmitaria.service.relatorio.pedido;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.repository.pedido.PedidoRepository;
import br.com.marmitaria.service.ingrediente.validator.IngredienteValidator;
import br.com.marmitaria.service.produto.validator.ProdutoValidator;
import br.com.marmitaria.service.relatorio.pedido.mapper.RelatorioPedidoMapper;
import br.com.marmitaria.service.usuario.validator.UsuarioValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class RelatorioPedidoContext {

    private final AuthenticatedUser authenticatedUser;
    private final UsuarioValidator usuarioValidator;
    private final PedidoRepository pedidoRepository;
    private final RelatorioPedidoMapper relatorioMapper;
    private final ProdutoValidator produtoValidator;
    private final IngredienteValidator ingredienteValidator;

}
