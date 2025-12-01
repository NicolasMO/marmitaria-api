package br.com.marmitaria.service.usuario;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.usuario.mapper.UsuarioMapper;
import br.com.marmitaria.service.usuario.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioContext {

    public final AuthenticatedUser authenticatedUser;
    public final UsuarioRepository usuarioRepository;
    public final UsuarioMapper usuarioMapper;
    public final UsuarioValidator usuarioValidator;

}
