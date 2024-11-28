package br.com.marmitaria.dto.usuario;

import lombok.Data;

@Data
public class CadastrarUsuarioDTO {
	private String nome;
	private String senha;
	private String email;
	private String celular;
}
