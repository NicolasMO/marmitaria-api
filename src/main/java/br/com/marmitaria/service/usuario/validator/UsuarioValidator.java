package br.com.marmitaria.service.usuario.validator;

import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.exception.usuario.UsuarioNaoEncontradoException;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public Usuario validar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException());
    }
}
