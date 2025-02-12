package br.com.marmitaria.dto.security;

import lombok.Data;

@Data
public class LoginRequestDTO {
	private String email;
	private String senha;
}
