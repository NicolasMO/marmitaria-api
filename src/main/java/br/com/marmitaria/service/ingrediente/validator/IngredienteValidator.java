package br.com.marmitaria.service.ingrediente.validator;

import br.com.marmitaria.dto.ingrediente.AtualizarIngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.exception.RequisicaoVaziaException;
import br.com.marmitaria.exception.ingrediente.IngredienteJaExistenteException;
import br.com.marmitaria.exception.ingrediente.IngredienteNaoEncontradoException;
import br.com.marmitaria.repository.ingrediente.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredienteValidator {

    private final IngredienteRepository ingredienteRepository;

    public void validarSeNomeExiste(String nome) {
        if (ingredienteRepository.existsByNomeIgnoreCase(nome)) throw new IngredienteJaExistenteException(nome);
    }

    public Ingrediente validar(Long id) {
        return ingredienteRepository.findById(id)
                .orElseThrow(() -> new IngredienteNaoEncontradoException(id));
    }

    public void validarRequisicaoVazia(AtualizarIngredienteDTO dto) {
        boolean nenhumCampoEnviado = (dto.nome() == null || dto.nome().isBlank()) && dto.categoria() == null;

        if (nenhumCampoEnviado) throw new RequisicaoVaziaException();
    }
}
