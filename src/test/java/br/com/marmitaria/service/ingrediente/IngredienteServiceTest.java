package br.com.marmitaria.service.ingrediente;

import br.com.marmitaria.dto.ingrediente.CadastroIngredienteDTO;
import br.com.marmitaria.dto.ingrediente.RespostaIngredienteDTO;
import br.com.marmitaria.entity.ingrediente.Ingrediente;
import br.com.marmitaria.factory.IngredienteFactoryTeste;
import br.com.marmitaria.repository.ingrediente.IngredienteRepository;
import br.com.marmitaria.service.ingrediente.factory.IngredienteFactory;
import br.com.marmitaria.service.ingrediente.mapper.IngredienteMapper;
import br.com.marmitaria.service.ingrediente.validator.IngredienteValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredienteServiceTest {

    @Mock private IngredienteContext contexto;
    @Mock private IngredienteValidator ingredienteValidator;
    @Mock private IngredienteFactory ingredienteFactory;
    @Mock private IngredienteRepository ingredienteRepository;
    @Mock private IngredienteMapper ingredienteMapper;

    @InjectMocks
    private IngredienteServiceImpl service;

    @BeforeEach
    void setup (){
        lenient().when(contexto.getIngredienteFactory()).thenReturn(ingredienteFactory);
        lenient().when(contexto.getIngredienteMapper()).thenReturn(ingredienteMapper);
        lenient().when(contexto.getIngredienteRepository()).thenReturn(ingredienteRepository);
        lenient().when(contexto.getIngredienteValidator()).thenReturn(ingredienteValidator);
    }

    @Test
    void deveCadastrarIngredienteComSucesso() {
        CadastroIngredienteDTO dto = IngredienteFactoryTeste.criarCadastroIngredienteDTO();
        Ingrediente ingrediente = IngredienteFactoryTeste.criarIngredienteProteina();
        RespostaIngredienteDTO ingredienteDTO = IngredienteFactoryTeste.criarRespostaIngredienteDTO();

        doNothing().when(ingredienteValidator).validarSeNomeExiste(dto.nome());
        when(ingredienteFactory.criarIngrediente(dto)).thenReturn(ingrediente);
        when(ingredienteMapper.paraDTO(ingrediente)).thenReturn(ingredienteDTO);

        RespostaIngredienteDTO resultado = service.cadastrarIngrediente(dto);

        InOrder inOrder = inOrder(ingredienteValidator, ingredienteFactory, ingredienteRepository, ingredienteMapper);

        inOrder.verify(ingredienteValidator).validarSeNomeExiste(dto.nome());
        inOrder.verify(ingredienteFactory).criarIngrediente(dto);
        inOrder.verify(ingredienteRepository).save(ingrediente);
        inOrder.verify(ingredienteMapper).paraDTO(ingrediente);

        assertEquals(ingredienteDTO, resultado);
    }

    @Test
    void deveListarIngredientePorId() {
        Ingrediente ingrediente = IngredienteFactoryTeste.criarIngredienteProteina();
        RespostaIngredienteDTO ingredienteDTO = IngredienteFactoryTeste.criarRespostaIngredienteDTO();

        when(ingredienteValidator.validar(1L)).thenReturn(ingrediente);
        when(ingredienteMapper.paraDTO(ingrediente)).thenReturn(ingredienteDTO);

        RespostaIngredienteDTO resultado = service.listarIngredientePorId(ingrediente.getId());

        verify(ingredienteValidator).validar(1L);
        verify(ingredienteMapper).paraDTO(ingrediente);

        assertEquals(ingredienteDTO, resultado);
    }

    @Test
    void deveListarTodosIngredientes() {
        List<Ingrediente> listaIngredientes = List.of(
                IngredienteFactoryTeste.criarIngredienteProteina(),
                IngredienteFactoryTeste.criarIngredienteProteina()
        );
        List<RespostaIngredienteDTO> listaDTO = List.of(
                IngredienteFactoryTeste.criarRespostaIngredienteDTO(),
                IngredienteFactoryTeste.criarRespostaIngredienteDTO()
        );

        when(ingredienteRepository.findAll()).thenReturn(listaIngredientes);
        when(ingredienteMapper.paraListaDTO(listaIngredientes)).thenReturn(listaDTO);

        List<RespostaIngredienteDTO> resultado = service.listarTodos();

        assertEquals(listaDTO, resultado);
        verify(ingredienteMapper).paraListaDTO(listaIngredientes);
    }

    @Test
    void deveRemoverIngrediente() {
        Ingrediente ingrediente = IngredienteFactoryTeste.criarIngredienteProteina();

        when(ingredienteValidator.validar(1L)).thenReturn(ingrediente);

        service.removerIngrediente(ingrediente.getId());

        InOrder inOrder = inOrder(ingredienteValidator, ingredienteRepository);
        inOrder.verify(ingredienteValidator).validar(ingrediente.getId());
        inOrder.verify(ingredienteRepository).delete(same(ingrediente));
    }
}
