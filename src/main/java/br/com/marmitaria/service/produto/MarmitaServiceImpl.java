package br.com.marmitaria.service.produto;

import static br.com.marmitaria.enums.TipoProduto.MARMITA_PEQUENA;
import static br.com.marmitaria.enums.TipoProduto.BEBIDA;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.marmitaria.dto.produto.MarmitaDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Marmita;
import br.com.marmitaria.entity.produto.MarmitaIngrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.enums.CategoriaIngrediente;
import br.com.marmitaria.enums.QuantidadeIngredientes;
import br.com.marmitaria.repository.produto.IngredienteRepository;
import br.com.marmitaria.repository.produto.MarmitaRepository;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class MarmitaServiceImpl implements MarmitaService {
	private final MarmitaRepository marmitaRepository;
	private final IngredienteRepository ingredienteRepository;
	private final ProdutoRepository produtoRepository;
	private final UsuarioRepository usuarioRepository;
	
	public MarmitaServiceImpl(MarmitaRepository marmitaRepository, IngredienteRepository ingredienteRepository,
								ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
		this.marmitaRepository = marmitaRepository;
		this.ingredienteRepository = ingredienteRepository;
		this.produtoRepository = produtoRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	public Marmita montarMarmita(MarmitaDTO marmitaDTO, Long produtoId, Long usuarioId) {
		Produto produto = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new RuntimeException("Produto não encontrado."));
		
		if(produto.getTipo() == BEBIDA) {
			throw new IllegalArgumentException("O produto não é uma marmita.");
		}
		
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
		
		QuantidadeIngredientes quantidadeIngredientes = produto.getTipo() == MARMITA_PEQUENA
				? QuantidadeIngredientes.MARMITA_PEQUENA : QuantidadeIngredientes.MARMITA_GRANDE;
		
		List<Ingrediente> ingredientes = ingredienteRepository.findAllById(marmitaDTO.getIngredientesId());
		
		validarIngredientes(quantidadeIngredientes, ingredientes);
		
		Marmita marmita = new Marmita();
		marmita.setProduto(produto);
		marmita.setUsuario(usuario);
		
		List<MarmitaIngrediente> marmitaIngredientes = ingredientes.stream()
				.map(ingrediente -> {
					MarmitaIngrediente mi = new MarmitaIngrediente();
					mi.setMarmita(marmita);
					mi.setIngrediente(ingrediente);
					return mi;
				}).collect(Collectors.toList());
		
		marmita.getIngredientes().addAll(marmitaIngredientes);
		
		return marmitaRepository.save(marmita);
		
	}

	private void validarIngredientes(QuantidadeIngredientes quantidadeIngredientes, List<Ingrediente> ingredientes) {
		long proteinas = ingredientes.stream().filter(i -> i.getCategoria() == CategoriaIngrediente.PROTEINA).count();
		long carboidratos = ingredientes.stream().filter(i -> i.getCategoria() == CategoriaIngrediente.CARBOIDRATO).count();
		long complementos = ingredientes.stream().filter(i -> i.getCategoria() == CategoriaIngrediente.COMPLEMENTO).count();
		
		if (proteinas == 0 || carboidratos == 0) {
	        throw new IllegalArgumentException("Adicione ingredientes.");
	    }
		
		if (proteinas > quantidadeIngredientes.getMaxPorCategoria(CategoriaIngrediente.PROTEINA)) {
			throw new IllegalArgumentException("Número máximo de proteínas atingido");
		}
		if (carboidratos > quantidadeIngredientes.getMaxPorCategoria(CategoriaIngrediente.CARBOIDRATO)) {
			throw new IllegalArgumentException("Número máximo de carboidratos atingido");
		}
		if (complementos > quantidadeIngredientes.getMaxPorCategoria(CategoriaIngrediente.COMPLEMENTO)) {
			throw new IllegalArgumentException("Número máximo de complementos atingido");
		}
	}
	
}
