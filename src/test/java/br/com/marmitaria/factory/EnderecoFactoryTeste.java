package br.com.marmitaria.factory;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.external.viacep.dto.RespostaViaCep;

public class EnderecoFactoryTeste {

    public static Endereco criarEndereco(Usuario usuario) {
        Endereco e = new Endereco(
                "Praça da Sé",
                "123",
                "Sé",
                "São Paulo",
                "SP",
                "Apto 45",
                "01001-000",
                usuario
        );
        e.setId(1L);
        return e;
    }

    public static CadastroEnderecoDTO criarCadastroEnderecoDTO() {
        return new CadastroEnderecoDTO(
                "01001-000",
                "123",
                "Apto 45"
        );
    }

    public static RespostaEnderecoDTO criarRespostaEnderecoDTO() {
        return new RespostaEnderecoDTO(
                1L,
                "Praça da Sé",
                "123",
                "Sé",
                "São Paulo",
                "SP",
                "Apto 45",
                "01001-000"
        );
    }

    public static RespostaViaCep criarRespostaViaCep() {
        return new RespostaViaCep(
                "01001-000",
                "Praça da Sé",
                "Sé",
                "São Paulo",
                "SP"
        );
    }
}
