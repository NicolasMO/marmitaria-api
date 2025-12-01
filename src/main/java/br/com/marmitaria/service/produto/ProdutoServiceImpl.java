package br.com.marmitaria.service.produto;

import br.com.marmitaria.dto.produto.AtualizarProdutoDTO;
import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
import br.com.marmitaria.dto.produto.RespostaProdutoDTO;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.exception.produto.ProdutoJaExistenteException;
import br.com.marmitaria.exception.produto.ProdutoNaoEncontradoException;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    @Transactional
    public Produto cadastrarProduto(CadastroProdutoDTO dto) {
        if (produtoRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new ProdutoJaExistenteException(dto.nome());
        }

        Produto produto = new Produto(
                dto.nome(),
                dto.precoUnitario(),
                dto.tipo()
        );

        return produtoRepository.save(produto);
    }

    @Override
    public List<RespostaProdutoDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(prod -> new RespostaProdutoDTO(
                prod.getId(),
                prod.getNome(),
                prod.getPrecoUnitario(),
                prod.getImagem(),
                prod.getTipo()
        )).toList();
    }

    @Override
    @Transactional
    public RespostaProdutoDTO atualizarProduto(Long id, AtualizarProdutoDTO dto) {
        if (produtoRepository.existsByNomeIgnoreCase(dto.nome())) {
            throw new ProdutoJaExistenteException(dto.nome());
        }

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        produto.atualizarDados(dto.nome(), dto.precoUnitario());
        produtoRepository.save(produto);

        return new RespostaProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPrecoUnitario(),
                produto.getImagem(),
                produto.getTipo()
        );
    }

    @Override
    @Transactional
    public void removerProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        produtoRepository.delete(produto);
    }
}
