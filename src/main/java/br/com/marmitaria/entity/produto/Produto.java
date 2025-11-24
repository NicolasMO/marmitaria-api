package br.com.marmitaria.entity.produto;

import br.com.marmitaria.enums.TipoProduto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotBlank
	@Column(nullable = false, length = 255)
	private String nome;

    @NotNull
    @Digits(integer = 4, fraction = 2)
    @DecimalMin("0.00")
    @DecimalMax("9999.99")
	@Column(name = "preco_unitario", nullable = false, precision = 6, scale = 2)
	private BigDecimal precoUnitario;

    @NotBlank
	@Column(nullable = false)
	private String imagem;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TipoProduto tipo;

    public Produto (String nome, BigDecimal precoUnitario, TipoProduto tipo) {
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.tipo = tipo;
    }

    public void atualizarDados(String nome, BigDecimal precoUnitario, TipoProduto tipo) {
        this.nome = nome;
        this.precoUnitario = precoUnitario;
        this.tipo = tipo;
    }
}
