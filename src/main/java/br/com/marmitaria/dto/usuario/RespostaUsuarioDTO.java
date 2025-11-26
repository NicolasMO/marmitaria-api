package br.com.marmitaria.dto.usuario;

import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;

import java.util.List;

public record RespostaUsuarioDTO(
        Long id,
        String nome,
        String email,
        String celular,
        String cpf,
        List<RespostaEnderecoDTO> enderecos
) {}
