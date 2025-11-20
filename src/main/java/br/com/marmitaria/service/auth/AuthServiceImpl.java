package br.com.marmitaria.service.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.marmitaria.config.security.JwtUtil;
import br.com.marmitaria.dto.security.LoginRequestDTO;
import br.com.marmitaria.dto.security.LoginResponseDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class AuthServiceImpl implements AuthService {

	private final UsuarioRepository usuarioRepository;
	private final JwtUtil jwtUtil;

	public AuthServiceImpl(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
		this.usuarioRepository = usuarioRepository;
		this.jwtUtil = jwtUtil;
	}



	@Override
	public LoginResponseDTO autenticar(LoginRequestDTO loginRequest) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
		
		if(usuario.isPresent() && usuario.get().getSenha().equals(loginRequest.getSenha())) {
			String token = jwtUtil.gerarToken(usuario.get());
			
			return new LoginResponseDTO(token, usuario.get().getNome(), usuario.get().getId());
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
		}
	}

}
