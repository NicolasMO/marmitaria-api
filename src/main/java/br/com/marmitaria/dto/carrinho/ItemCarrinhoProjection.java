package br.com.marmitaria.dto.carrinho;

public interface ItemCarrinhoProjection {
    Long getId();
    String getProdutoNome();
    Integer getQuantidade();
    Double getItemPreco();
    Long getMarmitaId();
}