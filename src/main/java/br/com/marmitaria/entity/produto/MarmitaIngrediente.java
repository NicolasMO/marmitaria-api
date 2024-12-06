package br.com.marmitaria.entity.produto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.marmitaria.entity.ingrediente.Ingrediente;
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
@Table(name = "marmita_ingrediente")
public class MarmitaIngrediente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "marmita_id", nullable = false)
	private Marmita marmita;
	
	@ManyToOne
	@JoinColumn(name = "ingrediente_id", nullable = false)
	private Ingrediente ingrediente;
}
