package br.com.marmitaria.service.produto;

import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
import br.com.marmitaria.dto.produto.RespostaProdutoDTO;
import br.com.marmitaria.entity.produto.Produto;
import jakarta.validation.Valid;

import java.util.List;

public interface ProdutoService {
    Produto cadastrarProduto(CadastroProdutoDTO dto);
    List<RespostaProdutoDTO> listarTodos();
    RespostaProdutoDTO atualizarProduto(Long id, CadastroProdutoDTO dto);
    void removerProduto(Long id);
}
