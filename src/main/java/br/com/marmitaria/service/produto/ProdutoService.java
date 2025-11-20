package br.com.marmitaria.service.produto;

import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
import br.com.marmitaria.entity.produto.Produto;
import jakarta.validation.Valid;

public interface ProdutoService {
    Produto cadastrarProduto(CadastroProdutoDTO dto);
}
