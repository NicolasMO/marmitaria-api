package br.com.marmitaria.external.viacep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RespostaViaCep(
        String cep,
        String logradouro,
        String bairro,

        @JsonProperty("localidade")
        String cidade,

        @JsonProperty("uf")
        String estado
) {}
