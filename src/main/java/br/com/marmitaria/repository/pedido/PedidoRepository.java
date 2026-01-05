package br.com.marmitaria.repository.pedido;

import br.com.marmitaria.entity.pedido.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findByDataPedidoBetween(
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable paginacao
    );

    Page<Pedido> findByUsuarioId(Long usuarioId, Pageable paginacao);

    @Query("""
                SELECT p
                FROM Pedido p
                JOIN p.itens i
                JOIN i.produto prod
                WHERE LOWER(prod.nome) IN :produtos
                GROUP BY p
                HAVING COUNT(DISTINCT prod.id) = :quantidade
            """)
    Page<Pedido> findPedidosComTodosProdutos(
            @Param("produtos") List<String> produtos,
            @Param("quantidade") long quantidade,
            Pageable pageable
    );

    @Query("""
                select p
                from Pedido p
                join p.itens i
                join i.ingredientes ing
                where lower(ing.nome) in :ingredientes
                group by p.id
                having count(distinct lower(ing.nome)) = :quantidade
            """)
    Page<Pedido> findPedidosComTodosIngredientes(
            @Param("ingredientes") List<String> ingredientes,
            @Param("quantidade") long quantidade,
            Pageable pageable
    );

}
