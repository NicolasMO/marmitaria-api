package br.com.marmitaria.service.produto;

import br.com.marmitaria.dto.produto.AtualizarProdutoDTO;
import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
import br.com.marmitaria.dto.produto.RespostaProdutoDTO;
import br.com.marmitaria.entity.produto.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoContext contexto;

    @Override
    @Transactional
    public Produto cadastrarProduto(CadastroProdutoDTO dto) {
        contexto.getProdutoValidator().validarSeNomeExiste(dto.nome());
        Produto produto = contexto.getProdutoFactory().criarProduto(dto);
        return contexto.getProdutoRepository().save(produto);
    }

    @Override
    public List<RespostaProdutoDTO> listarTodos() {
        List<Produto> produtos = contexto.getProdutoRepository().findAll();
        return contexto.getProdutoMapper().paraListaDTO(produtos);
    }

    @Override
    @Transactional
    public RespostaProdutoDTO atualizarProduto(Long id, AtualizarProdutoDTO dto) {
        contexto.getProdutoValidator().validarSeNomeExiste(dto.nome());
        Produto produto = contexto.getProdutoValidator().validar(id);
        produto.atualizarDados(dto.nome(), dto.precoUnitario());
        contexto.getProdutoRepository().save(produto);
        return contexto.getProdutoMapper().paraDTO(produto);
    }

    @Override
    @Transactional
    public void removerProduto(Long id) {
        Produto produto = contexto.getProdutoValidator().validar(id);
        contexto.getProdutoRepository().delete(produto);
    }
}
