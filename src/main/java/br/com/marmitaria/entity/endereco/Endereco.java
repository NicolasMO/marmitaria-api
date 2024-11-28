package br.com.marmitaria.entity.endereco;

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
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

}
