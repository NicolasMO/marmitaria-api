package br.com.marmitaria.entity.pedido;

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
@Table(name = "pedido_item")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

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
            name = "pedido_item_ingrediente",
            joinColumns = @JoinColumn(name = "pedido_item_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private Set<Ingrediente> ingredientes;

}
