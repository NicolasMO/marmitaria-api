package br.com.marmitaria.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
	private String token;
	private String usuarioNome;
	private Long usuarioId;
	private Long carrinhoId;
}
