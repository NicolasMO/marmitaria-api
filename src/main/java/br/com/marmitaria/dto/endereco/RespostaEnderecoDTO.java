package br.com.marmitaria.dto.endereco;

public record RespostaEnderecoDTO(
        Long id,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String estado,
        String complemento,
        String cep
) {}