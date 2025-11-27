package br.com.marmitaria.repository.pedido;

import br.com.marmitaria.entity.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
