package br.com.marmitaria.entity.carrinho;

import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "carrinho_item")
public class CarrinhoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carrinho_id", nullable = false)
    private Carrinho carrinho;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Min(1)
    @Max(50)
    @Column(nullable = false)
    private Integer quantidade;

    @Column(length = 255)
    private String observacao;

    @ManyToMany
    @JoinTable(
            name = "carrinho_item_ingrediente",
            joinColumns = @JoinColumn(name = "carrinho_item_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )

    private Set<Ingrediente> ingredientes;

    public CarrinhoItem(Carrinho carrinho, Produto produto, Integer quantidade, String observacao, Set<Ingrediente> ingredientes) {
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
        this.observacao = observacao;
        this.ingredientes = ingredientes;
    }
}
