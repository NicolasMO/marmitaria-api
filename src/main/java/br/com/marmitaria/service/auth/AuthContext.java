package br.com.marmitaria.service.auth;

import br.com.marmitaria.config.security.JwtUtil;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.auth.factory.AuthFactory;
import br.com.marmitaria.service.auth.validator.AuthValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class AuthContext {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthValidator authValidator;
    private final AuthFactory authFactory;
    private final JwtUtil jwtUtil;

}
