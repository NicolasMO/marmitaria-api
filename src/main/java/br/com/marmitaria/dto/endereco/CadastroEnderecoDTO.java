package br.com.marmitaria.dto.endereco;

import jakarta.validation.constraints.NotBlank;

public record CadastroEnderecoDTO(
        @NotBlank
        String cep,

        @NotBlank
        String numero,

        String complemento
) {}
