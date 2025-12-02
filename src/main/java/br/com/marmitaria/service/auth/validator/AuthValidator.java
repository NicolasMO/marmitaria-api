package br.com.marmitaria.service.auth.validator;

import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.exception.auth.AuthCPFJaCadastradoException;
import br.com.marmitaria.exception.auth.AuthDadosInvalidosException;
import br.com.marmitaria.exception.auth.AuthEmailJaCadastradoException;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthValidator {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario validarEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(AuthDadosInvalidosException::new);
    }

    public void validarSenha(String senhaDTO, String senhaCodificada) {
        if (!passwordEncoder.matches(senhaDTO, senhaCodificada)) {
            throw new AuthDadosInvalidosException();
        };
    }

    public void validarSeEmailOuCpfExistem (String email, String cpf) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new AuthEmailJaCadastradoException();
        }

        if (usuarioRepository.existsByCpf(cpf)) {
            throw new AuthCPFJaCadastradoException();
        }
    }

}
