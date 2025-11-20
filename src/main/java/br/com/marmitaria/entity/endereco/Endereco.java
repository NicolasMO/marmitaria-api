package br.com.marmitaria.entity.endereco;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.marmitaria.entity.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank
	@Column(nullable = false, length = 255)
	private String logradouro;

    @NotBlank
	@Column(nullable = false, length = 20)
	private String numero;

    @NotBlank
	@Column(nullable = false, length = 150)
	private String bairro;

    @NotBlank
	@Column(nullable = false, length = 150)
	private String cidade;

    @NotBlank
	@Column(nullable = false, length = 100)
	private String estado;

    @NotBlank
	@Column(nullable = false, length = 255)
	private String complemento;

    @NotBlank
	@Column(nullable = false, length = 20)
	private String cep;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	public Endereco(String logradouro, String numero, String bairro, String cidade, String estado, String complemento,
			String cep, Usuario usuario) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.complemento = complemento;
		this.cep = cep;
		this.usuario = usuario;
	}
	
	

}
