package br.com.marmitaria.repository.carrinho;

import br.com.marmitaria.dto.carrinho.RespostaTotaisCarrinhoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    Optional<Carrinho> findByUsuarioId(Long usuarioId);

    @Query("""
            SELECT sum(ci.quantidade) as totalProdutos,
                   sum(ci.quantidade * ci.produto.precoUnitario) as valorTotal
            FROM CarrinhoItem ci
            where ci.carrinho.id = :carrinhoId
            """)
    RespostaTotaisCarrinhoDTO calcularTotais(@Param("carrinhoId") Long carrinhoId);

}
