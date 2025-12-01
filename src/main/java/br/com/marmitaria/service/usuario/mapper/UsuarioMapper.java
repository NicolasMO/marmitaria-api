package br.com.marmitaria.service.usuario.mapper;

import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    public RespostaUsuarioDTO paraDTO(Usuario usuario) {
        return new RespostaUsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCelular(),
                usuario.getCpf(),
                usuario.getEnderecos().stream()
                        .map(this::paraEnderecoDTO)
                        .toList()
        );
    }

    public List<RespostaUsuarioDTO> paraListaDTO(List<Usuario> usuarios) {
        return usuarios.stream().map(this::paraDTO).toList();
    }

    private RespostaEnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return new RespostaEnderecoDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getComplemento(),
                endereco.getCep()
        );
    }
}
