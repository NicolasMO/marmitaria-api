package br.com.marmitaria.entity.carrinho;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.marmitaria.entity.produto.Marmita;
import br.com.marmitaria.entity.produto.Produto;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item_carrinho")	
public class ItemCarrinho {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "carrinho_id", nullable = false)
    private Carrinho carrinho;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
    
    @ManyToOne
    @JoinColumn(name = "marmita_id")
    private Marmita marmita;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private Double preco;
    
    public void atualizarQuantidade(int novaQuantidade) {
        this.quantidade = novaQuantidade;
    }
}
