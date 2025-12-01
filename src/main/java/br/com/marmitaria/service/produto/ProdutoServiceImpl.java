package br.com.marmitaria.service.produto;

import br.com.marmitaria.dto.produto.AtualizarProdutoDTO;
import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
import br.com.marmitaria.dto.produto.RespostaProdutoDTO;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.exception.produto.ProdutoJaExistenteException;
import br.com.marmitaria.exception.produto.ProdutoNaoEncontradoException;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        contexto.produtoValidator.validarSeNomeExiste(dto.nome());
        Produto produto = contexto.produtoFactory.criarProduto(dto);
        return contexto.produtoRepository.save(produto);
    }

    @Override
    public List<RespostaProdutoDTO> listarTodos() {
        List<Produto> produtos = contexto.produtoRepository.findAll();
        return contexto.produtoMapper.paraListaDTO(produtos);
    }

    @Override
    @Transactional
    public RespostaProdutoDTO atualizarProduto(Long id, AtualizarProdutoDTO dto) {
        contexto.produtoValidator.validarSeNomeExiste(dto.nome());
        Produto produto = contexto.produtoValidator.validar(id);
        produto.atualizarDados(dto.nome(), dto.precoUnitario());
        contexto.produtoRepository.save(produto);
        return contexto.produtoMapper.paraDTO(produto);
    }

    @Override
    @Transactional
    public void removerProduto(Long id) {
        Produto produto = contexto.produtoValidator.validar(id);
        contexto.produtoRepository.delete(produto);
    }
}
