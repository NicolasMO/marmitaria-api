package br.com.marmitaria.entity.usuario;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.marmitaria.entity.endereco.Endereco;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank
	@Column(nullable = false, length = 255)
	private String nome;

    @NotBlank
    @Email
	@Column(nullable = false, unique = true, length = 255)
	private String email;

    @CPF
    @Column(nullable = false, unique = true, length = 11)
	private String cpf;

    @NotBlank
	@Column(nullable = false, length = 11)
	private String celular;

    @NotBlank
    @Getter(AccessLevel.NONE)
	@Column(nullable = false)
	private String senha;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;
	
	public Usuario(String nome, String email, String cpf, String celular, String senha) {
		this.nome = nome;
        this.email = email;
        this.cpf = cpf;
		this.celular = celular;
		this.senha = senha;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

    @Override
    public String getUsername() {
        return email;
    }

	@Override
	public String getPassword() {
		return senha;
	}

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}
