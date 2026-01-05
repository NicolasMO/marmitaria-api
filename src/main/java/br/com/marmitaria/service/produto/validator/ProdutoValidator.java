package br.com.marmitaria.service.produto.validator;

import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.exception.produto.ProdutoJaExistenteException;
import br.com.marmitaria.exception.produto.ProdutoNaoEncontradoException;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void validarTodosExistem(List<String> nomes) {
        List<String> existentes =
                produtoRepository.findNomesExistentes(nomes);

        List<String> naoEncontrados = nomes.stream()
                .filter(nome -> !existentes.contains(nome))
                .toList();

        if (!naoEncontrados.isEmpty()) {
            throw new ProdutoNaoEncontradoException(naoEncontrados);
        }
    }
}
