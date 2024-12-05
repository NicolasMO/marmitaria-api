package br.com.marmitaria.repository.carrinho;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.dto.carrinho.QtdItemCarrinhoDTO;
import br.com.marmitaria.entity.carrinho.ItemCarrinho;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
	
	  @Query(value = "select ic.quantidade as quantidade, ic.produto_id as produtoId, ic.preco as preco from item_carrinho ic "
			  + "where ic.carrinho_id = :carrinhoId and ic.produto_id = :produtoId", nativeQuery = true)
	  QtdItemCarrinhoDTO buscarQuantidadeDoProduto(@Param(value = "carrinhoId") Long carrinhoId,
												   @Param(value = "produtoId") Long produtoId);
	
	  @Query("SELECT ic FROM ItemCarrinho ic WHERE ic.carrinho.id = :carrinhoId AND ic.produto.id = :produtoId")
	  Optional<ItemCarrinho> findByCarrinhoAndProduto(@Param("carrinhoId") Long carrinhoId, @Param("produtoId") Long produtoId);
}
