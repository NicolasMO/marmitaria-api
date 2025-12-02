package br.com.marmitaria.service.carrinho;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.service.carrinho.factory.CarrinhoFactory;
import br.com.marmitaria.service.carrinho.mapper.CarrinhoMapper;
import br.com.marmitaria.service.carrinho.validator.CarrinhoValidator;
import br.com.marmitaria.service.produto.validator.ProdutoValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class CarrinhoContext {

    private final CarrinhoRepository carrinhoRepository;
    private final AuthenticatedUser authenticatedUser;
    private final CarrinhoMapper carrinhoMapper;
    private final CarrinhoValidator carrinhoValidator;
    private final CarrinhoFactory carrinhoFactory;
    private final ProdutoValidator produtoValidator;

}
