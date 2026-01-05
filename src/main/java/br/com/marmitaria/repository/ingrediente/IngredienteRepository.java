package br.com.marmitaria.repository.ingrediente;

import br.com.marmitaria.enums.CategoriaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.entity.ingrediente.Ingrediente;

import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long>{
    boolean existsByNomeIgnoreCaseAndCategoria(String nome, CategoriaIngrediente categoria);
    boolean existsByNomeIgnoreCase(String nome);

    @Query("""
                select lower(i.nome)
                from Ingrediente i
                where lower(i.nome) in :nomes
            """)
    List<String> findNomesExistentes(@Param("nomes") List<String> nomes);
}
