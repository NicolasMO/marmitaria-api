package br.com.marmitaria.service.produto;

import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
