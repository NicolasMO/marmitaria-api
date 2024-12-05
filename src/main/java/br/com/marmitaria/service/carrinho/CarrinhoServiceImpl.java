package br.com.marmitaria.service.carrinho;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import br.com.marmitaria.dto.carrinho.AdicionarItemCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.ItemCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.QtdItemCarrinhoDTO;
import br.com.marmitaria.dto.produto.IngredienteDTO;
import br.com.marmitaria.dto.produto.ProdutoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.ItemCarrinho;
import br.com.marmitaria.entity.produto.Marmita;
import br.com.marmitaria.entity.produto.MarmitaIngrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.carrinho.ItemCarrinhoRepository;
import br.com.marmitaria.repository.produto.IngredienteRepository;
import br.com.marmitaria.repository.produto.MarmitaRepository;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

	private final CarrinhoRepository carrinhoRepository;
	private final ItemCarrinhoRepository itemCarrinhoRepository;
	private final MarmitaRepository marmitaRepository;
	private final ProdutoRepository produtoRepository;
	private final UsuarioRepository usuarioRepository;
	private final IngredienteRepository ingredienteRepository;

	public CarrinhoServiceImpl(CarrinhoRepository carrinhoRepository, ItemCarrinhoRepository itemCarrinhoRepository,
			ProdutoRepository produtoRepository, MarmitaRepository marmitaRepository,
			UsuarioRepository usuarioRepository, IngredienteRepository ingredienteRepository) {
		this.carrinhoRepository = carrinhoRepository;
		this.itemCarrinhoRepository = itemCarrinhoRepository;
		this.produtoRepository = produtoRepository;
		this.marmitaRepository = marmitaRepository;
		this.usuarioRepository = usuarioRepository;
		this.ingredienteRepository= ingredienteRepository;
	}

	public Carrinho buscarCarrinhoPorUsuario(Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		Carrinho carrinho = carrinhoRepository.findByUsuario(usuario)
				.orElseThrow(() -> new RuntimeException("Carrinho não encontrado para o usuário"));

		return carrinho;
	}

	public List<ItemCarrinhoDTO> listarItensCarrinho(Long carrinhoId) {
		Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
				.orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

		List<ItemCarrinhoDTO> itensDTO = new ArrayList<>();
		for (ItemCarrinho item : carrinho.getItens()) {
			ItemCarrinhoDTO itemDTO = new ItemCarrinhoDTO();
			ProdutoDTO produtoDTO = new ProdutoDTO();
			produtoDTO.setId(item.getProduto().getId());
			produtoDTO.setNome(item.getProduto().getNome());
			produtoDTO.setPreco(item.getProduto().getPreco());
			produtoDTO.setTipo(item.getProduto().getTipo());

			itemDTO.setProduto(produtoDTO);
			itemDTO.setQuantidade(item.getQuantidade());
			itemDTO.setPreco(item.getPreco());

			if (item.getMarmita() != null) {
				Marmita marmita = item.getMarmita();
				List<IngredienteDTO> ingredientesDTO = new ArrayList<>();
				for (MarmitaIngrediente marmitaIngrediente : marmita.getIngredientes()) {
					IngredienteDTO ingredienteDTO = new IngredienteDTO();
					ingredienteDTO.setNome(marmitaIngrediente.getIngrediente().getNome());
					ingredienteDTO.setCategoria(marmitaIngrediente.getIngrediente().getCategoria());
					ingredientesDTO.add(ingredienteDTO);
				}
				itemDTO.setIngredientes(ingredientesDTO);
			}

			itensDTO.add(itemDTO);
		}

		return itensDTO;
	}

	public void adicionarItemCarrinho(AdicionarItemCarrinhoDTO dto) {
		Carrinho carrinho = carrinhoRepository.findByUsuarioId(dto.getUsuarioId())
				.orElseGet(() -> criarCarrinhoParaUsuario(dto.getUsuarioId()));
		
	    Produto produto = produtoRepository.findById(dto.getProdutoId())
	    		.orElseThrow(() -> new RuntimeException("Produto não encontrado."));
	    
	    QtdItemCarrinhoDTO itemExistente = itemCarrinhoRepository.buscarQuantidadeDoProduto(carrinho.getId(), dto.getProdutoId());
	    
	    if (itemExistente != null) {
	    	ItemCarrinho itemCarrinho = itemCarrinhoRepository.findByCarrinhoAndProduto(carrinho.getId(), dto.getProdutoId())
	    			.orElseThrow(() -> new RuntimeException("Erro ao buscar item"));
    		itemCarrinho.setQuantidade(dto.getQuantidade());
    		itemCarrinho.setPreco(produto.getPreco() * dto.getQuantidade());
    		itemCarrinhoRepository.save(itemCarrinho);
	    } else {
	    	ItemCarrinho novoItem = new ItemCarrinho();
	    	novoItem.setCarrinho(carrinho);
	    	novoItem.setProduto(produto);
	    	novoItem.setQuantidade(dto.getQuantidade());
	    	novoItem.setPreco(produto.getPreco() * dto.getQuantidade());
	    	
	    	carrinho.adicionarItem(novoItem);
	        itemCarrinhoRepository.save(novoItem);
	    }
	    
	    carrinho.recalcularValorTotal(); 
	    carrinhoRepository.save(carrinho);

	}

	private Carrinho criarCarrinhoParaUsuario(Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
	    Carrinho novoCarrinho = new Carrinho();
	    novoCarrinho.setUsuario(usuario);
	    return carrinhoRepository.save(novoCarrinho);
	}

}
