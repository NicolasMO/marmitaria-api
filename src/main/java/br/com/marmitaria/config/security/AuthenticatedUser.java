package br.com.marmitaria.config.security;

import br.com.marmitaria.entity.usuario.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {

    public Long getId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado.");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof Usuario usuario)) {
            throw new RuntimeException("Principal inválido no contexto de autenticação.");
        }

        return usuario.getId();
    }
}
