package br.com.marmitaria.repository.ingrediente;

import br.com.marmitaria.enums.CategoriaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.entity.ingrediente.Ingrediente;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long>{
    boolean existsByNomeIgnoreCaseAndCategoria(String nome, CategoriaIngrediente categoria);
    boolean existsByNomeIgnoreCase(String nome);
}
