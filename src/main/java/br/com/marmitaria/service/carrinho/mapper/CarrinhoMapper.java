package br.com.marmitaria.service.carrinho.mapper;

import br.com.marmitaria.dto.carrinho.RespostaCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.RespostaTotaisCarrinhoDTO;
import br.com.marmitaria.dto.item.RespostaItemDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarrinhoMapper {
    public RespostaCarrinhoDTO paraDTO(Carrinho carrinho, RespostaTotaisCarrinhoDTO dto) {
        List<RespostaItemDTO> itens = carrinho.getItens().stream()
                .map(item -> new RespostaItemDTO(
                        item.getId(),
                        item.getProduto().getNome(),
                        item.getProduto().getPrecoUnitario(),
                        item.getQuantidade(),
                        item.getObservacao(),
                        item.getIngredientes().stream()
                                .map(Ingrediente::getNome)
                                .collect(Collectors.toSet())
                )).toList();

        return new RespostaCarrinhoDTO(
                carrinho.getId(),
                carrinho.getUsuario().getNome(),
                itens,
                dto.getTotalProdutos(),
                dto.getValorTotal()
        );
    }
}
