package br.com.marmitaria.service.carrinho.validator;

import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.exception.carrinho.CarrinhoNaoEncontradoException;
import br.com.marmitaria.exception.carrinho.CarrinhoVazioException;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarrinhoValidator {

    @Autowired
    CarrinhoRepository carrinhoRepository;

    public Carrinho validar(Long usuarioId) {

        Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new CarrinhoNaoEncontradoException());

        if (carrinho.getItens().isEmpty()) {
            throw new CarrinhoVazioException();
        }

        return carrinho;
    }
}
