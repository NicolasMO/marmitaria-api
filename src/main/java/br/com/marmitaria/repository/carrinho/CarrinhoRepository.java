package br.com.marmitaria.repository.carrinho;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.entity.carrinho.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
	Optional<Carrinho> findByUsuarioId(Long usuarioId);
}
