package br.com.marmitaria.service.auth;

import java.util.Optional;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.auth.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.marmitaria.config.security.JwtUtil;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

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
}
