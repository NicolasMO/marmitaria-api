package br.com.marmitaria.entity.carrinho;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.marmitaria.entity.usuario.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carrinho")
public class Carrinho {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	@JsonIgnore
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemCarrinho> itens = new ArrayList<>();
	
	@Column(nullable = false)
	private Double valorTotal = 0.0;
	
	public void adicionarItem(ItemCarrinho item) {
	    this.itens.add(item);
	    recalcularValorTotal();
	}

	public void removerItem(ItemCarrinho item) {
	    this.itens.remove(item);
	    recalcularValorTotal();
	}

	public void recalcularValorTotal() {
	    this.valorTotal = this.itens.stream()
	        .mapToDouble(item -> item.getPreco() * item.getQuantidade())
	        .sum();
	}
}
