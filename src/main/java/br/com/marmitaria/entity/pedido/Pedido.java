package br.com.marmitaria.entity.pedido;

import br.com.marmitaria.entity.usuario.Usuario;
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

    @NotBlank
    @Column(name = "forma_pagamento", nullable = false, length = 50)
    private String formaPagamento;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String status;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens = new ArrayList<>();
}
