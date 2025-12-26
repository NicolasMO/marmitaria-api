package br.com.marmitaria.repository.pedido;

import br.com.marmitaria.entity.pedido.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findByDataPedidoBetween(
            LocalDateTime inicio,
            LocalDateTime fim,
            Pageable paginacao
    );
}
