package br.com.marmitaria.external.viacep.service;

import br.com.marmitaria.external.viacep.dto.RespostaViaCep;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate = new RestTemplate();

    public RespostaViaCep buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        RespostaViaCep response = restTemplate.getForObject(url, RespostaViaCep.class);

        if (response == null || response.logradouro() == null) {
            throw new IllegalArgumentException("CEP inválido ou não encontrado.");
        }

        return response;
    }
}
