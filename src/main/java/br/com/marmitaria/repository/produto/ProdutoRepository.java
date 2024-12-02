package br.com.marmitaria.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.entity.produto.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
