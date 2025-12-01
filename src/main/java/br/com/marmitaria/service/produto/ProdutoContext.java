package br.com.marmitaria.service.produto;

import br.com.marmitaria.repository.produto.ProdutoRepository;
import br.com.marmitaria.service.produto.factory.ProdutoFactory;
import br.com.marmitaria.service.produto.mapper.ProdutoMapper;
import br.com.marmitaria.service.produto.validator.ProdutoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoContext {

    public final ProdutoRepository produtoRepository;
    public final ProdutoValidator produtoValidator;
    public final ProdutoMapper produtoMapper;
    public final ProdutoFactory produtoFactory;

}
