package br.com.marmitaria.entity.usuario;

import java.util.List;

import br.com.marmitaria.entity.endereco.Endereco;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false, length = 11)
	private String celular;
	
	@Column(nullable = false)
	private String senha;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Endereco> enderecos;
	
	public Usuario(String nome, String senha, String celular, String email) {
		this.nome = nome;
		this.senha = senha;
		this.celular = celular;
        this.email = email;
    }
}
