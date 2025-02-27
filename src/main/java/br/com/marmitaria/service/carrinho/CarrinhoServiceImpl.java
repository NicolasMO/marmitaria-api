package br.com.marmitaria.service.carrinho;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.dto.carrinho.AdicionarItemCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.CarrinhoResponseDTO;
import br.com.marmitaria.dto.carrinho.ItemCarrinhoDTO;
import br.com.marmitaria.dto.carrinho.ItemCarrinhoProjection;
import br.com.marmitaria.dto.carrinho.QtdItemCarrinhoDTO;
import br.com.marmitaria.dto.produto.MarmitaIngredienteProjection;
import br.com.marmitaria.dto.produto.ProdutoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.ItemCarrinho;
import br.com.marmitaria.entity.produto.Marmita;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.enums.CategoriaIngrediente;
import br.com.marmitaria.enums.TipoProduto;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.carrinho.ItemCarrinhoRepository;
import br.com.marmitaria.repository.produto.MarmitaIngredienteRepository;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.produto.MarmitaService;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

	private final CarrinhoRepository carrinhoRepository;
	private final ItemCarrinhoRepository itemCarrinhoRepository;
	private final MarmitaService marmitaService;
	private final MarmitaIngredienteRepository marmitaIngredienteRepository;
	private final ProdutoRepository produtoRepository;
	private final UsuarioRepository usuarioRepository;

	public CarrinhoServiceImpl(CarrinhoRepository carrinhoRepository, ItemCarrinhoRepository itemCarrinhoRepository,
			ProdutoRepository produtoRepository, MarmitaService marmitaService,
			MarmitaIngredienteRepository marmitaIngredienteRepository, UsuarioRepository usuarioRepository) {
		this.carrinhoRepository = carrinhoRepository;
		this.itemCarrinhoRepository = itemCarrinhoRepository;
		this.produtoRepository = produtoRepository;
		this.marmitaService = marmitaService;
		this.marmitaIngredienteRepository = marmitaIngredienteRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public CarrinhoResponseDTO listarItensDoCarrinho(Long carrinhoId) {
		List<ItemCarrinhoProjection> itensCarrinho = itemCarrinhoRepository.findByCarrinhoId(carrinhoId);

		List<Long> marmitaIds = itensCarrinho.stream().filter(item -> item.getMarmitaId() != null)
				.map(ItemCarrinhoProjection::getMarmitaId).collect(Collectors.toList());

		List<MarmitaIngredienteProjection> ingredientes = marmitaIngredienteRepository.findByMarmitaIds(marmitaIds);

		Map<Long, Map<String, String>> ingredientesPorMarmita = ingredientes.stream().collect(
				Collectors.groupingBy(MarmitaIngredienteProjection::getMarmitaId, Collectors.groupingBy(ingrediente -> {
					String categoriaNome = ingrediente.getIngredienteCategoria();

					CategoriaIngrediente categoria = CategoriaIngrediente.valueOf(categoriaNome);
					return categoria.name().toLowerCase();
				}, Collectors.mapping(MarmitaIngredienteProjection::getIngredienteNome, Collectors.joining(", ")))));

		List<ItemCarrinhoDTO> itens = itensCarrinho.stream().map(item -> {
			ProdutoDTO produto = new ProdutoDTO(item.getProdutoId(), item.getProdutoNome(), item.getItemPreco(),
					item.getMarmitaId() != null ? formatarIngredientes(ingredientesPorMarmita.get(item.getMarmitaId()))
							: null);
			return new ItemCarrinhoDTO(item.getId(), produto, item.getQuantidade(),
					item.getQuantidade() * item.getItemPreco());
		}).collect(Collectors.toList());

		Double valorTotal = itens.stream().mapToDouble(ItemCarrinhoDTO::getPrecoTotal).sum();

		return new CarrinhoResponseDTO(itens, valorTotal);
	}

	@Transactional
	public void adicionarItemAoCarrinho(AdicionarItemCarrinhoDTO dto) {
		Carrinho carrinho = carrinhoRepository.findByUsuarioId(dto.getUsuarioId())
				.orElseGet(() -> criarCarrinhoParaUsuario(dto.getUsuarioId()));

		Produto produto = produtoRepository.findById(dto.getProdutoId())
				.orElseThrow(() -> new RuntimeException("Produto não encontrado."));

		if (produto.getTipo() == TipoProduto.BEBIDA) {
			adicionarBebidaAoCarrinho(carrinho, produto, dto.getQuantidade());
		} else {
			Marmita marmita = marmitaService.montarMarmita(dto.getMarmitaDTO(), dto.getProdutoId(), dto.getUsuarioId());
			adicionarMarmitaAoCarrinho(carrinho, marmita);
		}

		carrinho.recalcularValorTotal();
		carrinhoRepository.save(carrinho);
	}

	@Transactional
	public void limparCarrinho(Long carrinhoId) {
		itemCarrinhoRepository.limparCarrinho(carrinhoId);
	}

	@Transactional
	public void removerItemDoCarrinho(Long itemId, Long carrinhoId) {
		itemCarrinhoRepository.removerItemDoCarrinho(itemId, carrinhoId);
	}

	@Override
	public Carrinho criarCarrinhoParaUsuario(Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
		Carrinho novoCarrinho = new Carrinho();
		novoCarrinho.setUsuario(usuario);
		return carrinhoRepository.save(novoCarrinho);
	}

	// Metodos privados

	private void adicionarMarmitaAoCarrinho(Carrinho carrinho, Marmita marmita) {
		ItemCarrinho itemCarrinho = new ItemCarrinho();
		itemCarrinho.setCarrinho(carrinho);
		itemCarrinho.setProduto(marmita.getProduto());
		itemCarrinho.setMarmita(marmita);
		itemCarrinho.setQuantidade(1);
		itemCarrinho.setPreco(marmita.getProduto().getPreco());
		itemCarrinhoRepository.save(itemCarrinho);
		carrinho.adicionarItem(itemCarrinho);
	}

	private void adicionarBebidaAoCarrinho(Carrinho carrinho, Produto produto, Integer quantidade) {
		QtdItemCarrinhoDTO itemExistente = itemCarrinhoRepository.buscarQuantidadeDoProdutoNoCarrinho(carrinho.getId(),
				produto.getId());

		if (itemExistente != null) {
			ItemCarrinho itemCarrinho = itemCarrinhoRepository
					.findByCarrinhoAndProduto(carrinho.getId(), produto.getId())
					.orElseThrow(() -> new RuntimeException("Erro ao buscar item"));
			itemCarrinho.setQuantidade(quantidade);
			itemCarrinho.setPreco(produto.getPreco() * quantidade);
			itemCarrinhoRepository.save(itemCarrinho);
		} else {
			ItemCarrinho novoItem = new ItemCarrinho();
			novoItem.setCarrinho(carrinho);
			novoItem.setProduto(produto);
			novoItem.setQuantidade(quantidade);
			novoItem.setPreco(produto.getPreco() * quantidade);

			carrinho.adicionarItem(novoItem);
			itemCarrinhoRepository.save(novoItem);
		}
	}

	private Map<String, String> formatarIngredientes(Map<String, String> ingredientesPorCategoria) {
		if (ingredientesPorCategoria == null || ingredientesPorCategoria.isEmpty())
			return null;

		Map<String, String> ingredientesFormatados = new HashMap<>();

		for (CategoriaIngrediente categoria : CategoriaIngrediente.values()) {
			String categoriaNome = categoria.name().toLowerCase();
			String ingredientesCategoria = ingredientesPorCategoria.get(categoriaNome);

			if (ingredientesCategoria == null || ingredientesCategoria.isEmpty()) {
				ingredientesCategoria = "Sem complementos";
			}

			ingredientesFormatados.put(categoriaNome, ingredientesCategoria);
		}

		return ingredientesFormatados;
	}

}
