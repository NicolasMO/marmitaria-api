package br.com.marmitaria.service.endereco.factory;

import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.external.viacep.dto.RespostaViaCep;
import org.springframework.stereotype.Component;

@Component
public class EnderecoFactory {

    public Endereco criarEndereco(CadastroEnderecoDTO dto, Usuario usuario, RespostaViaCep viaCep) {
        return new Endereco(
                viaCep.logradouro(),
                dto.numero(),
                viaCep.bairro(),
                viaCep.cidade(),
                viaCep.estado(),
                dto.complemento(),
                dto.cep(),
                usuario
        );
    }
}
