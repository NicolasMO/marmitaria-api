package br.com.marmitaria.service.auth.factory;

import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthFactory {

    private final PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(CadastroUsuarioDTO dto) {
        return new Usuario(
                dto.nome(),
                dto.email(),
                dto.cpf(),
                dto.celular(),
                passwordEncoder.encode(dto.senha())
        );
    }

}
