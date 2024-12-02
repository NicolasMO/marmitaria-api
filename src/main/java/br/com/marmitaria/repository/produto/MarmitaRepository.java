package br.com.marmitaria.repository.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.entity.produto.Marmita;

@Repository
public interface MarmitaRepository extends JpaRepository<Marmita, Long>{

}
