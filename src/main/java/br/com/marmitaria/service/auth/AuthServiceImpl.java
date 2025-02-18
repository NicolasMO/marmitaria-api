package br.com.marmitaria.service.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.marmitaria.config.security.JwtUtil;
import br.com.marmitaria.dto.security.LoginRequestDTO;
import br.com.marmitaria.dto.security.LoginResponseDTO;
import br.com.marmitaria.entity.carrinho.Carrinho;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.carrinho.CarrinhoService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UsuarioRepository usuarioRepository;
	private final CarrinhoService carrinhoService;
	private final CarrinhoRepository carrinhoRepository;
	private final JwtUtil jwtUtil;

	public AuthServiceImpl(UsuarioRepository usuarioRepository, CarrinhoRepository carrinhoRepository,
			CarrinhoService carrinhoService, JwtUtil jwtUtil) {
		this.usuarioRepository = usuarioRepository;
		this.carrinhoRepository = carrinhoRepository;
		this.carrinhoService = carrinhoService;
		this.jwtUtil = jwtUtil;
	}



	@Override
	public LoginResponseDTO autenticar(LoginRequestDTO loginRequest) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
		
		if(usuario.isPresent() && usuario.get().getSenha().equals(loginRequest.getSenha())) {
			String token = jwtUtil.gerarToken(usuario.get());
			Carrinho carrinho = carrinhoRepository.findByUsuarioId(usuario.get().getId())
					.orElseGet(() -> carrinhoService.criarCarrinhoParaUsuario(usuario.get().getId()));
			
			return new LoginResponseDTO(token, usuario.get().getNome(), usuario.get().getId(), carrinho.getId());
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
		}
	}

}
