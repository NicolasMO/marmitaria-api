package br.com.marmitaria.service.produto;

import br.com.marmitaria.repository.produto.ProdutoRepository;
import br.com.marmitaria.service.produto.factory.ProdutoFactory;
import br.com.marmitaria.service.produto.mapper.ProdutoMapper;
import br.com.marmitaria.service.produto.validator.ProdutoValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class ProdutoContext {

    private final ProdutoRepository produtoRepository;
    private final ProdutoValidator produtoValidator;
    private final ProdutoMapper produtoMapper;
    private final ProdutoFactory produtoFactory;

}
