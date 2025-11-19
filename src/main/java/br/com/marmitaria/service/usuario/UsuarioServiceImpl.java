package br.com.marmitaria.service.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
	private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Codigo antigo

	private final CarrinhoRepository carrinhoRepository;
	private final CarrinhoService carrinhoService;
    private final JwtUtil jwtUtil;



    public UsuarioServiceImpl(CarrinhoRepository carrinhoRepository,
    							CarrinhoService carrinhoService, JwtUtil jwtUtil) {
        this.carrinhoRepository = carrinhoRepository;
        this.carrinhoService = carrinhoService;
        this.jwtUtil = jwtUtil;
    }

    // Codigo antigo

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
	public Usuario cadastrarUsuario(CadastrarUsuarioDTO dto) {
		if (usuarioRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        if (usuarioRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException(("CPF já cadastrado."));
        }

        Usuario usuario = new Usuario(
                dto.nome(),
                dto.email(),
                dto.cpf(),
                dto.celular(),
                passwordEncoder.encode(dto.senha())
        );

        return usuarioRepository.save(usuario);
	}

    @Override
    @Transactional
    public void removerUsuario(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }
}
