package br.com.marmitaria.service.produto.factory;

import br.com.marmitaria.dto.produto.CadastroProdutoDTO;
import br.com.marmitaria.entity.produto.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoFactory {

    public Produto criarProduto(CadastroProdutoDTO dto) {
        return new Produto(
                dto.nome(),
                dto.precoUnitario(),
                dto.tipo()
        );
    }
}
