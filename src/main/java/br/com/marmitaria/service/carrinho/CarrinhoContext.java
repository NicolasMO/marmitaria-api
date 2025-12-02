package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.carrinho.RespostaTotaisCarrinhoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
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

    private final CarrinhoRepository carrinhoRepository;
    private final AuthenticatedUser authenticatedUser;
    private final CarrinhoMapper carrinhoMapper;
    private final CarrinhoValidator carrinhoValidator;
    private final CarrinhoFactory carrinhoFactory;
    private final ProdutoValidator produtoValidator;

    public CarrinhoRepository carrinhoRepository() {
        return carrinhoRepository;
    }

    public AuthenticatedUser authenticatedUser() {
        return authenticatedUser;
    }

    public CarrinhoMapper carrinhoMapper() {
        return carrinhoMapper;
    }

    public CarrinhoValidator carrinhoValidator() {
        return carrinhoValidator;
    }

    public CarrinhoFactory carrinhoFactory() {
        return carrinhoFactory;
    }

    public ProdutoValidator produtoValidator() {
        return produtoValidator;
    }

}
