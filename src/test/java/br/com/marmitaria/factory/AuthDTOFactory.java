package br.com.marmitaria.factory;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;

public class AuthDTOFactory {

    public static LoginDTO loginValido() {
        return new LoginDTO("teste@email.com", "123456");
    }

    public static CadastroUsuarioDTO cadastroValido() {
        return new CadastroUsuarioDTO("João", "teste@email.com",
                "12345678900", "85999990000", "senha123");
    }
}