package br.com.marmitaria.repository.produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.dto.produto.MarmitaIngredienteProjection;
import br.com.marmitaria.entity.produto.MarmitaIngrediente;

@Repository
public interface MarmitaIngredienteRepository extends JpaRepository<MarmitaIngrediente, Long>{
	
	@Query(value="SELECT mi.marmita_id AS marmitaId, i.nome AS ingredienteNome, i.categoria AS ingredienteCategoria "
			+ "FROM marmita_ingrediente mi "
			+ "JOIN ingrediente i ON mi.ingrediente_id = i.id "
			+ "WHERE mi.marmita_id IN (:marmitaIds) "
			+ "ORDER BY mi.marmita_id ASC", nativeQuery = true)
	List<MarmitaIngredienteProjection> findByMarmitaIds(@Param("marmitaIds") List<Long> marmitaIds);
	
}
