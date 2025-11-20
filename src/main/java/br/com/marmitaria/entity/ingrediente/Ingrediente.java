package br.com.marmitaria.entity.ingrediente;

import br.com.marmitaria.enums.CategoriaIngrediente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ingrediente")
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaIngrediente categoria;

    public Ingrediente (String nome, CategoriaIngrediente categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    public void atualizarDados (String nome, CategoriaIngrediente categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }
}
