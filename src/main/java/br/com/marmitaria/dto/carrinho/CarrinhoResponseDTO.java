package br.com.marmitaria.dto.carrinho;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoResponseDTO {
	private List<ItemCarrinhoDTO> itens;
	private Double valorTotal;
	}