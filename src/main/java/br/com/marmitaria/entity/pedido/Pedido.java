package br.com.marmitaria.entity.pedido;

import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.enums.FormaPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @Digits(integer = 8, fraction = 2)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @NotBlank
    @Column(name = "endereco_entrega", nullable = false, length = 255)
    private String enderecoEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false, length = 50)
    private FormaPagamento formaPagamento;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens = new ArrayList<>();

    public Pedido(Usuario usuario, String enderecoEntrega, FormaPagamento formaPagamento) {
        this.usuario = usuario;
        this.dataPedido = LocalDateTime.now();
        this.enderecoEntrega = enderecoEntrega;
        this.formaPagamento = formaPagamento;
        this.status = "PAGAMENTO NA ENTREGA";
    }
}
