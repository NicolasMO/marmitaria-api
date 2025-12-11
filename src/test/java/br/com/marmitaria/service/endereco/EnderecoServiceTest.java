package br.com.marmitaria.service.endereco;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.endereco.CadastroEnderecoDTO;
import br.com.marmitaria.dto.endereco.RespostaEnderecoDTO;
import br.com.marmitaria.entity.endereco.Endereco;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.external.viacep.dto.RespostaViaCep;
import br.com.marmitaria.external.viacep.service.ViaCepService;
import br.com.marmitaria.factory.EnderecoFactoryTeste;
import br.com.marmitaria.factory.UsuarioFactoryTeste;
import br.com.marmitaria.repository.endereco.EnderecoRepository;
import br.com.marmitaria.service.endereco.factory.EnderecoFactory;
import br.com.marmitaria.service.endereco.mapper.EnderecoMapper;
import br.com.marmitaria.service.endereco.validator.EnderecoValidator;
import br.com.marmitaria.service.usuario.validator.UsuarioValidator;
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
public class EnderecoServiceTest {

    @Mock private EnderecoContext contexto;
    @Mock private EnderecoRepository enderecoRepository;
    @Mock private UsuarioValidator usuarioValidator;
    @Mock private EnderecoValidator enderecoValidator;
    @Mock private EnderecoFactory enderecoFactory;
    @Mock private EnderecoMapper enderecoMapper;
    @Mock private AuthenticatedUser authenticatedUser;
    @Mock private ViaCepService viaCepService;

    @InjectMocks
    private EnderecoServiceImpl service;

    @BeforeEach
    void setup () {
        lenient().when(contexto.getEnderecoRepository()).thenReturn(enderecoRepository);
        lenient().when(contexto.getEnderecoFactory()).thenReturn(enderecoFactory);
        lenient().when(contexto.getEnderecoValidator()).thenReturn(enderecoValidator);
        lenient().when(contexto.getEnderecoMapper()).thenReturn(enderecoMapper);
        lenient().when(contexto.getUsuarioValidator()).thenReturn(usuarioValidator);
        lenient().when(contexto.getAuthenticatedUser()).thenReturn(authenticatedUser);
        lenient().when(contexto.getViaCepService()).thenReturn(viaCepService);
    }

    @Test
    void deveCadastrarEnderecoComSucesso() {
        CadastroEnderecoDTO dto = EnderecoFactoryTeste.criarCadastroEnderecoDTO();
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();
        RespostaViaCep cepInfo = EnderecoFactoryTeste.criarRespostaViaCep();
        Endereco endereco = new Endereco();
        RespostaEnderecoDTO enderecoDTO = EnderecoFactoryTeste.criarRespostaEnderecoDTO();

        when(authenticatedUser.getId()).thenReturn(usuario.getId());
        when(usuarioValidator.validar(usuario.getId())).thenReturn(usuario);
        when(viaCepService.buscarCep(dto.cep())).thenReturn(cepInfo);
        when(enderecoFactory.criarEndereco(dto, usuario, cepInfo)).thenReturn(endereco);
        when(enderecoMapper.paraDTO(endereco)).thenReturn(enderecoDTO);

        RespostaEnderecoDTO resultado = service.cadastrarEndereco(dto);

        InOrder inOrder = inOrder(authenticatedUser, usuarioValidator,
                enderecoValidator, viaCepService, enderecoFactory,
                enderecoRepository, enderecoMapper);

        inOrder.verify(authenticatedUser).getId();
        inOrder.verify(usuarioValidator).validar(usuario.getId());
        inOrder.verify(enderecoValidator).validarQuantidadeMaxima(usuario.getEnderecos());
        inOrder.verify(enderecoValidator).validarDuplicidade(usuario.getId(), dto);
        inOrder.verify(viaCepService).buscarCep(dto.cep());
        inOrder.verify(enderecoFactory).criarEndereco(dto, usuario, cepInfo);
        inOrder.verify(enderecoRepository).save(endereco);
        inOrder.verify(enderecoMapper).paraDTO(endereco);

        assertEquals(enderecoDTO, resultado);
    }

    @Test
    void deveListarEnderecosDoUsuario() {
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();
        List<Endereco> enderecos = List.of(EnderecoFactoryTeste.criarEndereco(usuario));
        usuario.setEnderecos(enderecos);
        List<RespostaEnderecoDTO> listaDTO = List.of(EnderecoFactoryTeste.criarRespostaEnderecoDTO());

        when(authenticatedUser.getId()).thenReturn(usuario.getId());
        when(usuarioValidator.validar(usuario.getId())).thenReturn(usuario);
        when(enderecoMapper.paraListaDTO(usuario.getEnderecos())).thenReturn(listaDTO);

        List<RespostaEnderecoDTO> resultado = service.listarEnderecosDoUsuario();

        assertEquals(listaDTO, resultado);
        verify(enderecoMapper).paraListaDTO(enderecos);
    }

    @Test
    void deveListarEnderecoDoUsuarioPorId() {
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();
        Endereco endereco = EnderecoFactoryTeste.criarEndereco(usuario);
        RespostaEnderecoDTO enderecoDTO = EnderecoFactoryTeste.criarRespostaEnderecoDTO();

        when(authenticatedUser.getId()).thenReturn(usuario.getId());
        when(enderecoValidator.validar(endereco.getId(), usuario.getId())).thenReturn(endereco);
        when(enderecoMapper.paraDTO(endereco)).thenReturn(enderecoDTO);

        RespostaEnderecoDTO resultado = service.listarEnderecoPorID(endereco.getId());

        verify(enderecoValidator).validar(endereco.getId(), usuario.getId());
        verify(enderecoMapper).paraDTO(endereco);

        assertEquals(enderecoDTO, resultado);
    }

    @Test
    void deveRemoverEnderecoDoUsuario() {
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();
        Endereco endereco = EnderecoFactoryTeste.criarEndereco(usuario);

        when(authenticatedUser.getId()).thenReturn(usuario.getId());
        when(enderecoValidator.validar(endereco.getId(), usuario.getId())).thenReturn(endereco);

        service.removerEnderecoDoUsuario(endereco.getId());

        InOrder inOrder = inOrder(authenticatedUser, enderecoValidator, enderecoRepository);
        inOrder.verify(authenticatedUser).getId();
        inOrder.verify(enderecoValidator).validar(endereco.getId(), usuario.getId());
        inOrder.verify(enderecoRepository).delete(same(endereco));
    }
}
