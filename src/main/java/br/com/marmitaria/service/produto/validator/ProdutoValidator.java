package br.com.marmitaria.service.produto.validator;

import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.exception.produto.ProdutoJaExistenteException;
import br.com.marmitaria.exception.produto.ProdutoNaoEncontradoException;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoValidator {

    private final ProdutoRepository produtoRepository;

    public void validarSeNomeExiste(String nome) {
        if (produtoRepository.existsByNomeIgnoreCase(nome)) {
            throw new ProdutoJaExistenteException(nome);
        }
    }

    public Produto validar(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }
}
