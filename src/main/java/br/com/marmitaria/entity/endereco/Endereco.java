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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String logradouro;
	
	@Column(nullable = false, length = 10)
	private String numero;
	
	@Column(nullable = false, length = 40)
	private String bairro;
	
	@Column(nullable = false, length = 40)
	private String cidade;
	
	@Column(nullable = false, length = 40)
	private String estado;
	
	@Column(nullable = false, length = 100)
	private String complemento;
	
	@Column(nullable = false, length = 8)
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
