package br.com.marmitaria.service.usuario;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.exception.auth.AuthUsuarioNaoAutenticado;
import br.com.marmitaria.exception.usuario.UsuarioNaoEncontradoException;
import br.com.marmitaria.factory.UsuarioFactoryTeste;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.service.usuario.mapper.UsuarioMapper;
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
public class UsuarioServiceTest {

    @Mock private UsuarioContext contexto;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioMapper usuarioMapper;
    @Mock private UsuarioValidator usuarioValidator;
    @Mock private AuthenticatedUser authenticatedUser;

    @InjectMocks
    private UsuarioServiceImpl service;

    @BeforeEach
    void setup() {
        lenient().when(contexto.getUsuarioRepository()).thenReturn(usuarioRepository);
        lenient().when(contexto.getUsuarioMapper()).thenReturn(usuarioMapper);
        lenient().when(contexto.getUsuarioValidator()).thenReturn(usuarioValidator);
        lenient().when(contexto.getAuthenticatedUser()).thenReturn(authenticatedUser);
    }

    @Test
    void deveListarTodosUsuarios() {
        Usuario u1 = UsuarioFactoryTeste.criarUsuarioConfirmado();
        Usuario u2 = UsuarioFactoryTeste.criarUsuarioNaoConfirmado();
        List<Usuario> listaUsuarios = List.of(u1, u2);

        RespostaUsuarioDTO dto1 = UsuarioFactoryTeste.usuarioCriadoDTO();
        RespostaUsuarioDTO dto2 = UsuarioFactoryTeste.usuarioCriadoDTO();
        List<RespostaUsuarioDTO> listaDTO = List.of(dto1, dto2);

        when(usuarioRepository.findAll()).thenReturn(listaUsuarios);
        when(usuarioMapper.paraListaDTO(listaUsuarios)).thenReturn(listaDTO);

        List<RespostaUsuarioDTO> resultado = service.listarTodos();

        verify(usuarioRepository).findAll();
        verify(usuarioMapper).paraListaDTO(listaUsuarios);

        assertEquals(listaDTO, resultado);
    }

    @Test
    void deveBuscarUsuarioAutenticado() {
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();
        RespostaUsuarioDTO dto = UsuarioFactoryTeste.usuarioCriadoDTO();

        when(authenticatedUser.getId()).thenReturn(1L);
        when(usuarioValidator.validar(1L)).thenReturn(usuario);
        when(usuarioMapper.paraDTO(usuario)).thenReturn(dto);

        RespostaUsuarioDTO resultado = service.buscarUsuarioAutenticado();

        InOrder inOrder = inOrder(authenticatedUser, usuarioValidator, usuarioMapper);

        inOrder.verify(authenticatedUser).getId();
        inOrder.verify(usuarioValidator).validar(1L);
        inOrder.verify(usuarioMapper).paraDTO(usuario);

        assertEquals(dto, resultado);
    }

    @Test
    void deveBuscarUsuarioPorId() {
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();
        RespostaUsuarioDTO dto = UsuarioFactoryTeste.usuarioCriadoDTO();

        when(usuarioValidator.validar(1L)).thenReturn(usuario);
        when(usuarioMapper.paraDTO(usuario)).thenReturn(dto);

        RespostaUsuarioDTO resultado = service.buscarUsuarioPorID(1L);

        verify(usuarioValidator).validar(1L);
        verify(usuarioMapper).paraDTO(usuario);
        assertEquals(dto, resultado);
    }

    @Test
    void deveRemoverUsuario() {
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();

        when(usuarioValidator.validar(1L)).thenReturn(usuario);

        service.removerUsuario(1L);

        InOrder inOrder = inOrder(usuarioValidator, usuarioRepository);
        inOrder.verify(usuarioValidator).validar(1L);
        inOrder.verify(usuarioRepository).delete(same(usuario));
    }

    @Test
    void naoDeveBuscarUsuarioQuandoNaoAutenticado() {
        when(authenticatedUser.getId()).thenThrow(new AuthUsuarioNaoAutenticado());

        AuthUsuarioNaoAutenticado exception = assertThrows(AuthUsuarioNaoAutenticado.class,
                () -> service.buscarUsuarioAutenticado());

        assertEquals("Usuário não autenticado.", exception.getMessage());
        verify(authenticatedUser).getId();
        verifyNoInteractions(usuarioValidator, usuarioMapper);
    }

    @Test
    void naoDeveBuscarUsuarioInexistente() {
        doThrow(new UsuarioNaoEncontradoException())
                .when(usuarioValidator)
                .validar(99L);

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class,
                () -> service.buscarUsuarioPorID(99L));

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verifyNoInteractions(usuarioMapper);
    }

    @Test
    void naoDeveRemoverUsuarioInexistente() {
        doThrow(new UsuarioNaoEncontradoException())
                .when(usuarioValidator)
                .validar(99L);

        UsuarioNaoEncontradoException exception = assertThrows(UsuarioNaoEncontradoException.class,
                () -> service.removerUsuario(99L));

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verifyNoInteractions(usuarioRepository);
    }
}
