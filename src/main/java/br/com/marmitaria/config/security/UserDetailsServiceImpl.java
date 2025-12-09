package br.com.marmitaria.config.security;

import java.util.ArrayList;

import br.com.marmitaria.exception.usuario.UsuarioNaoConfirmadoException;
import br.com.marmitaria.exception.usuario.UsuarioNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(UsuarioNaoEncontradoException::new);

        if (!usuario.isEnabled()) {
            throw new UsuarioNaoConfirmadoException();
        }

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                new ArrayList<>()
        );
     }
}
