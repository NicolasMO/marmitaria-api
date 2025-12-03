package br.com.marmitaria.service.endereco.validator;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.exception.endereco.EnderecoJaCadastradoException;
import br.com.marmitaria.exception.endereco.EnderecoLimiteCadastradoException;
import br.com.marmitaria.exception.endereco.EnderecoNaoEncontradoException;
import br.com.marmitaria.exception.endereco.EnderecoSemPermissaoException;
import br.com.marmitaria.repository.endereco.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EnderecoValidator {

    private final EnderecoRepository enderecoRepository;

    public Endereco validar(Long id, Long usuarioId) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(id));

        if (!endereco.getUsuario().getId().equals(usuarioId)) {
            throw new EnderecoSemPermissaoException();
        }

        return endereco;
    }

    public void validarDuplicidade(Long usuarioId, CadastroEnderecoDTO dto) {
        boolean existe = enderecoRepository.existsByUsuarioIdAndNumeroAndComplementoIgnoreCase(usuarioId, dto.numero(), dto.complemento());

        if (existe) {
            throw new EnderecoJaCadastradoException();
        }
    }

    public void validarQuantidadeMaxima(List<Endereco> enderecos) {
        if (enderecos.size() >= 3) {
            throw new EnderecoLimiteCadastradoException();
        }
    }
}
