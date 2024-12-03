package br.com.marmitaria.controller.produto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.dto.produto.MarmitaDTO;
import br.com.marmitaria.service.produto.MarmitaService;

@RestController
@RequestMapping("marmita")
public class MarmitaController {
	private final MarmitaService marmitaService;
	
	public MarmitaController(MarmitaService marmitaService) {
		this.marmitaService = marmitaService;
	}
	
	@PostMapping("/montar")
	public ResponseEntity<MarmitaDTO> criarMarmita(@RequestBody MarmitaDTO marmitaDTO) {
		marmitaService.criarMarmita(marmitaDTO);
		return new ResponseEntity<MarmitaDTO>(HttpStatus.CREATED);
	}
}
