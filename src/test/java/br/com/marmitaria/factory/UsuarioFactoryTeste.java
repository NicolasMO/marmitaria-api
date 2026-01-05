package br.com.marmitaria.factory;


import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;

import java.util.List;

public class UsuarioFactoryTeste {

    public static Usuario criarUsuarioNaoConfirmado() {
        Usuario u = new Usuario();
        u.setId(1L);
        u.setNome("Nicolas Teste");
        u.setEmail("teste@email.com");
        u.setSenha("senha123");
        u.setCpf("12345678900");
        u.setAtivo(false);
        return u;
    }

    public static Usuario criarUsuarioConfirmado() {
        Usuario usuario = criarUsuarioNaoConfirmado();
        usuario.setAtivo(true);
        return usuario;
    }

    public static RespostaUsuarioDTO usuarioCriadoDTO() {
        Usuario usuario = criarUsuarioNaoConfirmado();

        return new RespostaUsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCelular(),
                usuario.getCpf(),
                List.of()
        );
    }
}
