package br.com.marmitaria.config.security;

import br.com.marmitaria.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = jwtUtil.extrairEmail(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var usuario = usuarioRepository.findByEmail(email).orElse(null);

                    if (usuario != null && usuario.isEnabled() && jwtUtil.isTokenValido(token, usuario)) {
                        String role = jwtUtil.extrairRole(token);

                        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(usuario, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e){
            System.out.println("TOKEN INVÁLIDO: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}