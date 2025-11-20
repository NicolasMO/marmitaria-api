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
	@Column(nullable = false, length = 50)
	private String nome;

    @NotNull
    @Digits(integer = 4, fraction = 2)
    @DecimalMin("0.00")
    @DecimalMax("9999.99")
	@Column(nullable = false, precision = 6, scale = 2)
	private BigDecimal preco_unitario;

    @NotBlank
	@Column(nullable = false)
	private String imagem;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TipoProduto tipo;

    public Produto (String nome, BigDecimal preco_unitario, String imagem, TipoProduto tipo) {
        this.nome = nome;
        this.preco_unitario = preco_unitario;
        this.imagem = imagem;
        this.tipo = tipo;
    }

    public void atualizarDados(String nome, BigDecimal preco_unitario, TipoProduto tipo) {
        this.nome = nome;
        this.preco_unitario = preco_unitario;
        this.tipo = tipo;
    }
}
