package br.com.marmitaria.dto.carrinho;

import java.util.List;

import br.com.marmitaria.dto.produto.MarmitaDTO;
import lombok.Data;

@Data
public class AdicionarItemCarrinhoDTO {
	private Long usuarioId;
	private Long produtoId; 
	private Integer quantidade;
    private MarmitaDTO marmitaDTO; 
}
