package br.com.marmitaria.service.endereco.mapper;

import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnderecoMapper {

    public RespostaEnderecoDTO paraDTO(Endereco endereco) {
        return new RespostaEnderecoDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getComplemento(),
                endereco.getCep()
        );
    }

    public List<RespostaEnderecoDTO> paraListaDTO(List<Endereco> enderecos) {
        return enderecos.stream().map(this::paraDTO).toList();
    }
}
