package br.com.marmitaria.factory;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;

public class AuthDTOFactoryTeste {

    public static LoginDTO loginDTO() {
        return new LoginDTO("teste@email.com", "123456");
    }

    public static CadastroUsuarioDTO cadastroDTO() {
        return new CadastroUsuarioDTO("João", "teste@email.com",
                "12345678900", "85999990000", "senha123");
    }
}