package br.com.marmitaria.service.carrinho;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.dto.carrinho.AddCarrinhoDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.ItemCarrinho;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.produto.ProdutoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

	private final CarrinhoRepository carrinhoRepository;
	private final ProdutoRepository produtoRepository;
	private final UsuarioRepository usuarioRepository;
	
	public CarrinhoServiceImpl(CarrinhoRepository carrinhoRepository, ProdutoRepository produtoRepository,
			UsuarioRepository usuarioRepository) {
		this.carrinhoRepository = carrinhoRepository;
		this.produtoRepository = produtoRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	@Transactional
	public Carrinho adicionarItemCarrinho(AddCarrinhoDTO addCarrinhoDTO) {
		Produto produto = produtoRepository.findById(addCarrinhoDTO.getProdutoId())
				.orElseThrow(() -> new RuntimeException("Produto não encontrado."));
		
		 Carrinho carrinho = carrinhoRepository.findByUsuarioId(addCarrinhoDTO.getUsuarioId())
	                .orElseGet(() -> criarCarrinho(addCarrinhoDTO.getUsuarioId()));
		
		ItemCarrinho item = new ItemCarrinho();
		item.setCarrinho(carrinho);
		item.setProduto(produto);
		item.setQuantidade(addCarrinhoDTO.getQuantidade());
		item.setPreco(produto.getPreco() * addCarrinhoDTO.getQuantidade());
		
		carrinho.getItens().add(item);
		carrinho.setValorTotal(carrinho.getValorTotal() + item.getPreco());
		
		return carrinhoRepository.save(carrinho);
	}
	
	private Carrinho criarCarrinho(Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
		Carrinho carrinho = new Carrinho();
		carrinho.setUsuario(usuario);
		carrinho.setValorTotal(0.0);
		return carrinho;
	}
}
