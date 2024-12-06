package br.com.marmitaria.controller.produto;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.dto.produto.MarmitaDTO;
import br.com.marmitaria.entity.produto.Marmita;
import br.com.marmitaria.repository.produto.MarmitaRepository;
import br.com.marmitaria.service.produto.MarmitaService;

@RestController
@RequestMapping("marmita")
public class MarmitaController {
	private final MarmitaService marmitaService;
	private final MarmitaRepository marmitaRepository;
	
	public MarmitaController(MarmitaService marmitaService, MarmitaRepository marmitaRepository) {
		this.marmitaService = marmitaService;
		this.marmitaRepository = marmitaRepository;
	}
	
	@GetMapping
	public ResponseEntity<?> pegarMarmita() {
		List<Marmita> marmita = marmitaRepository.findAll() ;
		return ResponseEntity.ok(marmita);
	}
	
	@PostMapping("/montar")
	public ResponseEntity<MarmitaDTO> criarMarmita(@RequestBody MarmitaDTO marmitaDTO) {
		marmitaService.montarMarmita(marmitaDTO);
		return new ResponseEntity<MarmitaDTO>(HttpStatus.CREATED);
	}
}
