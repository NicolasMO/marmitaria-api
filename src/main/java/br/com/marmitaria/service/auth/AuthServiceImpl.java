package br.com.marmitaria.service.auth;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.auth.TokenDTO;
import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.marmitaria.config.security.JwtUtil;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public TokenDTO login(LoginDTO dto) {

        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos."));

        if (!passwordEncoder.matches(dto.senha(), usuario.getPassword())) {
            throw new IllegalArgumentException("Email ou senha inválidos.");
        };

        String token = jwtUtil.gerarToken(usuario);

        return new TokenDTO(token);
    }

    @Override
    @Transactional
    public Usuario cadastrarUsuario(CadastroUsuarioDTO dto) {
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
}
