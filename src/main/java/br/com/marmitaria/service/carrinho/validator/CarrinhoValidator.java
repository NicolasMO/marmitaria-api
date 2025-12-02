package br.com.marmitaria.service.carrinho.validator;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.carrinho.CarrinhoItem;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.entity.produto.Produto;
import br.com.marmitaria.exception.carrinho.*;
import br.com.marmitaria.exception.ingrediente.IngredienteListaNaoEncontradaException;
import br.com.marmitaria.exception.usuario.UsuarioNaoEncontradoException;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.ingrediente.IngredienteRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.carrinho.factory.RegraProdutoFactory;
import br.com.marmitaria.service.carrinho.strategy.RegraProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CarrinhoValidator {

    private final AuthenticatedUser authenticatedUser;
    private final CarrinhoRepository carrinhoRepository;
    private final UsuarioRepository usuarioRepository;
    private final IngredienteRepository ingredienteRepository;

    public Carrinho validarExistente(Long usuarioId) {
        return carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(CarrinhoNaoEncontradoException::new);
    }

    public Carrinho obterOuCriar(Long usuarioId) {
        return carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> new Carrinho(usuarioRepository.findById(usuarioId)
                        .orElseThrow(UsuarioNaoEncontradoException::new),
                        new ArrayList<>()));
    }

    public CarrinhoItem validarItem(Carrinho carrinho, Long itemId) {
        return carrinho.getItens().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(CarrinhoItemNaoEncontradoException::new);
    }

    public void validarAlteracaoQuantidade(CarrinhoItem item, Integer quantidade) {
        if (quantidade == null || quantidade < 1) throw new CarrinhoAlterarQuantidadeBebidaException("mínima", 1);

        if (item.getProduto().getTipo().isMarmita()) throw new CarrinhoAlterarQuantidadeMarmitaException();

        item.setQuantidade(quantidade);
    }

    public List<Ingrediente> validarIngredientes(List<Long> ids) {
        if (ids == null) return List.of();
        List<Ingrediente> encontrados = ingredienteRepository.findAllById(ids);

        if (ids.size() != encontrados.size()) throw new IngredienteListaNaoEncontradaException();

        return encontrados;
    }

    public void validarRegrasDoProduto(Produto produto, List<Ingrediente> ingredientes) {
        RegraProduto regra = RegraProdutoFactory.criar(produto.getTipo());
        regra.validar(produto, ingredientes);
    }

    public boolean tratarBebidaDuplicada(Carrinho carrinho, CarrinhoItem item) {
        if (item.getProduto().getTipo().isMarmita()) return false;

        return carrinho.getItens().stream()
                .filter(i -> i.getProduto().getId().equals(item.getProduto().getId()))
                .findFirst()
                .map(itemPresente -> {
                    itemPresente.setQuantidade(itemPresente.getQuantidade() + 1);
                    return true;
                })
                .orElse(false);
    }

    public void validarExclusaoCarrinho(Carrinho carrinho, Long usuarioId) {
        Carrinho existente = carrinhoRepository.findById(carrinho.getId())
                .orElseThrow(CarrinhoNaoEncontradoException::new);

        if(!existente.getUsuario().getId().equals(usuarioId)) throw new CarrinhoUsuarioSemPermissaoException("Você não tem permissão.");
    }
}
