package br.com.marmitaria.repository.endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.marmitaria.entity.endereco.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
