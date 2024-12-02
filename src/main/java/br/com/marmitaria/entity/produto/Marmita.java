package br.com.marmitaria.entity.produto;

import java.util.ArrayList;
import java.util.List;

import br.com.marmitaria.enums.TipoMarmita;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "marmita")
public class Marmita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "produto_id" ,nullable = false)
	private Produto produto;
	
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15) // Exemplo: "PEQUENA", "GRANDE"
    private TipoMarmita tipoMarmita;
	
	@OneToMany(mappedBy = "marmita", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MarmitaIngrediente> ingredientes = new ArrayList<>();
}
