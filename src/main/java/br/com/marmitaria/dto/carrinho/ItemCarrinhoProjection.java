package br.com.marmitaria.dto.carrinho;

public interface ItemCarrinhoProjection {
    Long getId();
    Long getProdutoId();
    String getProdutoNome();
    Integer getQuantidade();
    Double getItemPreco();
    Long getMarmitaId();
}