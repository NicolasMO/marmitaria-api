package br.com.marmitaria.dto.carrinho;

import java.util.List;

import lombok.Data;

@Data
public class AdicionarItemCarrinhoDTO {
	private Long usuarioId;
	private Long produtoId; 
    private Long marmitaId; 
    private Integer quantidade;
    private List<Long> ingredientesId;
}
