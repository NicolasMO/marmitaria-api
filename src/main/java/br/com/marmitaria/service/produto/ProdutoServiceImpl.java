package br.com.marmitaria.service.produto;

import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
import br.com.marmitaria.dto.produto.RespostaProdutoDTO;
import br.com.marmitaria.entity.produto.Produto;
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
        Produto produto = new Produto(
                dto.nome(),
                dto.preco_unitario(),
                dto.imagem(),
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
                prod.getPreco_unitario(),
                prod.getImagem(),
                prod.getTipo()
        )).toList();
    }

    @Override
    @Transactional
    public RespostaProdutoDTO atualizarProduto(Long id, CadastroProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        produto.atualizarDados(dto.nome(), dto.preco_unitario(), dto.imagem(), dto.tipo());
        produtoRepository.save(produto);

        return new RespostaProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco_unitario(),
                produto.getImagem(),
                produto.getTipo()
        );
    }

    @Override
    @Transactional
    public void removerProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        produtoRepository.delete(produto);
    }
}
