package br.com.marmitaria.service.pedido.mapper;

import br.com.marmitaria.dto.item.RespostaItemDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.pedido.PedidoItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoItemMapper {

    public RespostaItemDTO paraDTO(PedidoItem item) {
        return new RespostaItemDTO(
                item.getId(),
                item.getProduto().getNome(),
                item.getProduto().getPrecoUnitario(),
                item.getQuantidade(),
                item.getObservacao(),
                item.getIngredientes().stream().map(Ingrediente::getNome).collect(Collectors.toSet())
        );
    }

}
