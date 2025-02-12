package br.com.marmitaria.service.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.config.security.JwtUtil;
import br.com.marmitaria.dto.security.LoginRequestDTO;
import br.com.marmitaria.dto.security.LoginResponseDTO;
import br.com.marmitaria.dto.usuario.CadastrarUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }
	
    @Override
    public LoginResponseDTO autenticar(LoginRequestDTO loginRequest) {
    	Optional<Usuario> usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
    	
    	if(usuario.isPresent() && usuario.get().getSenha().equals(loginRequest.getSenha())) {
    		String token = jwtUtil.gerarToken(usuario.get().getEmail());
    		return new LoginResponseDTO(token);
    	} else {
    		throw new RuntimeException("Usuário ou senha inválidos");
    	}
    }
	
	@Override
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
		
	}
	
	@Override
	public Optional<Usuario> buscarUsuario(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	@Transactional
	public Usuario cadastrarUsuario(CadastrarUsuarioDTO cadastrarUsuarioDTO) {
		Usuario usuario = new Usuario(
				cadastrarUsuarioDTO.getNome(), 
				cadastrarUsuarioDTO.getSenha(),
				cadastrarUsuarioDTO.getCelular(),
				cadastrarUsuarioDTO.getEmail()
				);

		System.out.println(usuario);
		return usuarioRepository.save(usuario);
	}
}
