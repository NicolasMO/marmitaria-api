package br.com.marmitaria.service.usuario;

import java.util.List;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
	UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticatedUser authenticatedUser;

	@Override
	public List<RespostaUsuarioDTO> listarTodos() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream().map(usuario -> {
            List<RespostaEnderecoDTO> enderecosDTO = usuario.getEnderecos().stream()
                    .map(end -> new RespostaEnderecoDTO(
                            end.getId(),
                            end.getLogradouro(),
                            end.getNumero(),
                            end.getBairro(),
                            end.getCidade(),
                            end.getEstado(),
                            end.getComplemento(),
                            end.getCep()
                    ))
                    .toList();

            return new RespostaUsuarioDTO(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getCelular(),
                    usuario.getCpf(),
                    enderecosDTO
            );
        }).toList();
	}
	
	@Override
	public RespostaUsuarioDTO buscarUsuario() {
        Long usuarioId = authenticatedUser.getId();

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        List<RespostaEnderecoDTO> enderecosDTO = usuario.getEnderecos().stream()
                .map(end -> new RespostaEnderecoDTO(
                        end.getId(),
                        end.getLogradouro(),
                        end.getNumero(),
                        end.getBairro(),
                        end.getCidade(),
                        end.getEstado(),
                        end.getComplemento(),
                        end.getCep()
                ))
                .toList();

        return new RespostaUsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCelular(),
                usuario.getCpf(),
                enderecosDTO
        );

	}

    @Override
    public RespostaUsuarioDTO buscarUsuarioPorID(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        List<RespostaEnderecoDTO> enderecosDTO = usuario.getEnderecos().stream()
                .map(end -> new RespostaEnderecoDTO(
                        end.getId(),
                        end.getLogradouro(),
                        end.getNumero(),
                        end.getBairro(),
                        end.getCidade(),
                        end.getEstado(),
                        end.getComplemento(),
                        end.getCep()
                ))
                .toList();

        return new RespostaUsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCelular(),
                usuario.getCpf(),
                enderecosDTO
        );

    }

    @Override
    @Transactional
    public void removerUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }
}
