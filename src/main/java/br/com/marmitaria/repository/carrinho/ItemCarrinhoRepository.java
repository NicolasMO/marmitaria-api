package br.com.marmitaria.repository.carrinho;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.dto.carrinho.ItemCarrinhoProjection;
import br.com.marmitaria.dto.carrinho.QtdItemCarrinhoDTO;
import br.com.marmitaria.entity.carrinho.ItemCarrinho;

@Repository
public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
	
	  @Query(value = "SELECT ic.quantidade AS quantidade, ic.produto_id AS produtoId, ic.preco AS preco FROM item_carrinho ic "
			  + "WHERE ic.carrinho_id = :carrinhoId AND ic.produto_id = :produtoId", nativeQuery = true)
	  QtdItemCarrinhoDTO buscarQuantidadeDoProdutoNoCarrinho(@Param(value = "carrinhoId") Long carrinhoId,
												   @Param(value = "produtoId") Long produtoId);
	  
	  @Query(value = "SELECT  ic.id AS id, ic.quantidade AS quantidade, p.preco AS item_preco, "
			  + "p.id as produto_id, p.nome AS produto_nome, ic.marmita_id "
			  + "FROM item_carrinho ic "
			  + "LEFT JOIN produto p ON ic.produto_id = p.id "
			  + "WHERE ic.carrinho_id = :carrinhoId "
			  + "ORDER BY ic.id ASC", nativeQuery = true)
	  List<ItemCarrinhoProjection> findByCarrinhoId(@Param("carrinhoId") Long carrinhoId);
	
	  @Query(value = "SELECT * FROM item_carrinho ic WHERE ic.carrinho_id = :carrinhoId AND ic.produto_id = :produtoId", nativeQuery = true)
	  Optional<ItemCarrinho> findByCarrinhoAndProduto(@Param("carrinhoId") Long carrinhoId, @Param("produtoId") Long produtoId);
	  
	  @Modifying
	  @Query(value = "DELETE FROM item_carrinho ic WHERE ic.carrinho_id = :carrinhoId", nativeQuery = true)
	  void limparCarrinho(@Param("carrinhoId") Long carrinhoId);
	  
	  @Modifying
	  @Query(value = "DELETE FROM item_carrinho ic where ic.id = :itemId AND ic.carrinho_id = :carrinhoId", nativeQuery = true)
	  void removerItemDoCarrinho(@Param("itemId") Long itemId, @Param("carrinhoId") Long carrinhoId);
}
