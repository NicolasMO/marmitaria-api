package br.com.marmitaria.service.produto.mapper;

import br.com.marmitaria.dto.produto.RespostaProdutoDTO;
import br.com.marmitaria.entity.produto.Produto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoMapper {

    public RespostaProdutoDTO paraDTO(Produto produto) {
        return new RespostaProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPrecoUnitario(),
                produto.getImagem(),
                produto.getTipo()
        );
    }

    public List<RespostaProdutoDTO> paraListaDTO(List<Produto> produtos) {
        return produtos.stream().map(this::paraDTO).toList();
    }
}
