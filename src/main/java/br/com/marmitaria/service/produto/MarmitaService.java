package br.com.marmitaria.service.produto;

import br.com.marmitaria.dto.produto.MarmitaDTO;
import br.com.marmitaria.entity.produto.Marmita;

public interface MarmitaService  {
	public Marmita montarMarmita(MarmitaDTO marmitaDTO);
}
