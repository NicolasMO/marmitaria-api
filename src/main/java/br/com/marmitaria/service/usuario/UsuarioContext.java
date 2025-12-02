package br.com.marmitaria.service.usuario;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.usuario.mapper.UsuarioMapper;
import br.com.marmitaria.service.usuario.validator.UsuarioValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class UsuarioContext {

    private final AuthenticatedUser authenticatedUser;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioValidator usuarioValidator;

}
