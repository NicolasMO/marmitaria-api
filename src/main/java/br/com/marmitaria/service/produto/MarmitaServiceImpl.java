package br.com.marmitaria.service.produto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.marmitaria.dto.produto.MarmitaDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Marmita;
import br.com.marmitaria.entity.produto.MarmitaIngrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.enums.TipoMarmita;
import br.com.marmitaria.repository.produto.IngredienteRepository;
import br.com.marmitaria.repository.produto.MarmitaRepository;
import br.com.marmitaria.repository.produto.ProdutoRepository;

@Service
public class MarmitaServiceImpl implements MarmitaService {
	private final MarmitaRepository marmitaRepository;
	private final IngredienteRepository ingredienteRepository;
	private final ProdutoRepository produtoRepository;
	
	public MarmitaServiceImpl(MarmitaRepository marmitaRepository, IngredienteRepository ingredienteRepository,
								ProdutoRepository produtoRepository) {
		this.marmitaRepository = marmitaRepository;
		this.ingredienteRepository = ingredienteRepository;
		this.produtoRepository = produtoRepository;
	}
	
	public MarmitaDTO criarMarmita(MarmitaDTO marmitaDTO) {
		// Valida produto (Marmita pequena ou grande)
		Produto produto = produtoRepository.findById(marmitaDTO.getProdutoId())
				.orElseThrow(() -> new RuntimeException("Produto não encontrado."));
		
		TipoMarmita tipoMarmita = definirTipoMarmitaComBaseNoProduto(produto);
		// Valida ingredientes
		List<Ingrediente> ingredientes = ingredienteRepository.findAllById(marmitaDTO.getIngredientesId());
		if(ingredientes.size() != marmitaDTO.getIngredientesId().size()) {
			throw new RuntimeException("Alguns ingredientes não foram encontrados");
		}
		
		// Criar Marmita
		Marmita marmita = new Marmita();
		marmita.setTipoMarmita(tipoMarmita);
		marmita.setProduto(produto);
		List<MarmitaIngrediente> marmitaIngredientes = ingredientes.stream()
				.map(ing -> new MarmitaIngrediente(null, marmita, ing))
				.collect(Collectors.toList());
		marmita.setIngredientes(marmitaIngredientes);
		
		validarIngredientesPorTipo(marmita);
		
		// Salvar Marmita
        Marmita marmitaSalva = marmitaRepository.save(marmita);

        // Retornar DTO
        return new MarmitaDTO(
                marmitaSalva.getId(),
                marmitaSalva.getProduto().getId(),
                marmitaIngredientes.stream().map(mi -> mi.getIngrediente().getId()).collect(Collectors.toList())
        );
	}
	
	private TipoMarmita definirTipoMarmitaComBaseNoProduto(Produto produto) {
	    // Definindo o tipo de marmita com base no tipo de produto
	    if (produto.getId() == 1) {
	        return TipoMarmita.PEQUENA;
	    } else if (produto.getId() == 2) {
	        return TipoMarmita.GRANDE;
	    } else {
	        throw new RuntimeException("Tipo de produto inválido para definir tipo de marmita.");
	    }
    }
	
	private void validarIngredientesPorTipo(Marmita marmita) {
	    TipoMarmita tipo = marmita.getTipoMarmita();
	    List<MarmitaIngrediente> ingredientes = marmita.getIngredientes();

	    long qtdProteinas = contarIngredientesPorCategoria(ingredientes, "proteina");
	    long qtdCarboidratos = contarIngredientesPorCategoria(ingredientes, "carboidrato");
	    long qtdComplementos = contarIngredientesPorCategoria(ingredientes, "complemento");

	    if (qtdProteinas > tipo.getMaxProteinas()) {
	        throw new RuntimeException("Número de proteínas excede o permitido para este tipo de marmita.");
	    }
	    if (qtdCarboidratos > tipo.getMaxCarboidratos()) {
	        throw new RuntimeException("Número de carboidratos excede o permitido para este tipo de marmita.");
	    }
	    if (qtdComplementos > tipo.getMaxComplementos()) {
	        throw new RuntimeException("Número de complementos excede o permitido para este tipo de marmita.");
	    }
	}
	
	private long contarIngredientesPorCategoria(List<MarmitaIngrediente> ingredientes, String categoria) {
	    return ingredientes.stream()
	            .map(MarmitaIngrediente::getIngrediente)
	            .filter(ing -> ing.getCategoria().equalsIgnoreCase(categoria))
	            .count();
	}

}
