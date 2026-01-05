package br.com.marmitaria.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.entity.produto.Produto;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    boolean existsByNomeIgnoreCase(String nome);

    @Query("""
                select lower(p.nome)
                from Produto p
                where lower(p.nome) in :nomes
            """)
    List<String> findNomesExistentes(@Param("nomes") List<String> nomes);
}
