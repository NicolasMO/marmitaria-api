package br.com.marmitaria.dto.endereco;

import lombok.Data;

@Data
public class CadastrarEnderecoDTO {
	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String complemento;
	private String cep;
	private Long usuarioId;
}
