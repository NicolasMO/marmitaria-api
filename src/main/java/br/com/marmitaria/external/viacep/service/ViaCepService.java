package br.com.marmitaria.external.viacep.service;

import br.com.marmitaria.external.viacep.dto.ViaCepResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ViaCepResponse buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);

        if (response == null || response.logradouro() == null) {
            throw new IllegalArgumentException("CEP inválido ou não encontrado.");
        }

        return response;
    }
}
