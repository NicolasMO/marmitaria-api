package br.com.marmitaria.service.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.config.security.JwtUtil;
import br.com.marmitaria.dto.usuario.CadastrarUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.carrinho.CarrinhoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.carrinho.CarrinhoService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	private final CarrinhoRepository carrinhoRepository;
	private final CarrinhoService carrinhoService;
    private final JwtUtil jwtUtil;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, CarrinhoRepository carrinhoRepository,
    							CarrinhoService carrinhoService, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.carrinhoService = carrinhoService;
        this.jwtUtil = jwtUtil;
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
