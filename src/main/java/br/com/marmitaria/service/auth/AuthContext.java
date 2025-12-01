package br.com.marmitaria.service.auth;

import br.com.marmitaria.config.security.JwtUtil;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.auth.factory.AuthFactory;
import br.com.marmitaria.service.auth.validator.AuthValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthContext {

    public final UsuarioRepository usuarioRepository;
    public final PasswordEncoder passwordEncoder;
    public final AuthValidator authValidator;
    public final AuthFactory authFactory;
    public final JwtUtil jwtUtil;

}
