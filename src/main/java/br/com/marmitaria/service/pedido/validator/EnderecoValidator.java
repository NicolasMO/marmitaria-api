package br.com.marmitaria.service.pedido.validator;

import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.exception.endereco.EnderecoNaoEncontradoException;
import br.com.marmitaria.exception.endereco.EnderecoSemPermissaoException;
import br.com.marmitaria.repository.endereco.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoValidator {

    @Autowired
    EnderecoRepository enderecoRepository;

    public Endereco validar(Long id, Long usuarioId) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(id));

        if (!endereco.getUsuario().getId().equals(usuarioId)) {
            throw new EnderecoSemPermissaoException();
        }

        return endereco;
    }
}
