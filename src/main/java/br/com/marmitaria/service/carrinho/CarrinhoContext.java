package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.ingrediente.IngredienteRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.carrinho.factory.CarrinhoFactory;
import br.com.marmitaria.service.carrinho.mapper.CarrinhoMapper;
import br.com.marmitaria.service.carrinho.validator.CarrinhoValidator;
import br.com.marmitaria.service.produto.validator.ProdutoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarrinhoContext {

    public final AuthenticatedUser authenticatedUser;
    public final CarrinhoRepository carrinhoRepository;
    public final IngredienteRepository ingredienteRepository;
    public final UsuarioRepository usuarioRepository;

    public final CarrinhoMapper carrinhoMapper;
    public final CarrinhoValidator carrinhoValidator;
    public final CarrinhoFactory carrinhoFactory;

    public final ProdutoValidator produtoValidator;

}
