package br.com.marmitaria.service.auth;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.auth.TokenDTO;
import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.exception.auth.AuthCPFJaCadastradoException;
import br.com.marmitaria.exception.auth.AuthDadosInvalidosException;
import br.com.marmitaria.exception.auth.AuthEmailJaCadastradoException;
import br.com.marmitaria.exception.usuario.UsuarioNaoConfirmadoException;
import br.com.marmitaria.factory.AuthDTOFactory;
import br.com.marmitaria.factory.UsuarioFactoryTeste;
import br.com.marmitaria.service.auth.factory.AuthFactory;
import br.com.marmitaria.service.auth.validator.AuthValidator;
import br.com.marmitaria.service.email.EmailService;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.config.security.JwtUtil;

import br.com.marmitaria.service.usuario.mapper.UsuarioMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private AuthContext contexto;
    @Mock private AuthValidator authValidator;
    @Mock private AuthFactory authFactory;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private UsuarioMapper usuarioMapper;
    @Mock private JwtUtil jwtUtil;
    @Mock private EmailService emailService;

    @InjectMocks
    private AuthServiceImpl service;

    @BeforeEach
    void setup() {
        lenient().when(contexto.getAuthValidator()).thenReturn(authValidator);
        lenient().when(contexto.getAuthFactory()).thenReturn(authFactory);
        lenient().when(contexto.getUsuarioRepository()).thenReturn(usuarioRepository);
        lenient().when(contexto.getUsuarioMapper()).thenReturn(usuarioMapper);
        lenient().when(contexto.getJwtUtil()).thenReturn(jwtUtil);
        lenient().when(contexto.getEmailService()).thenReturn(emailService);
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        CadastroUsuarioDTO dto = AuthDTOFactory.cadastroDTO();
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioNaoConfirmado();
        RespostaUsuarioDTO usuarioCriado = UsuarioFactoryTeste.usuarioCriadoDTO();
        String tokenAtivacao = "token-123";

        when(authFactory.criarUsuario(dto)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.paraDTO(usuario)).thenReturn(usuarioCriado);
        when(jwtUtil.gerarTokenDeAtivacao(usuario)).thenReturn(tokenAtivacao);

        ArgumentCaptor<String> corpoEmailCaptor = ArgumentCaptor.forClass(String.class);

        RespostaUsuarioDTO resultado = service.cadastrarUsuario(dto);

        InOrder inOrder = inOrder(authValidator, authFactory, usuarioRepository, jwtUtil, emailService);
        inOrder.verify(authValidator).validarSeEmailOuCpfExistem(dto.email(), dto.cpf());
        inOrder.verify(authFactory).criarUsuario(dto);
        inOrder.verify(usuarioRepository).save(usuario);
        inOrder.verify(jwtUtil).gerarTokenDeAtivacao(usuario);
        inOrder.verify(emailService).enviarEmail(eq(dto.email()), anyString(), corpoEmailCaptor.capture());

        assertTrue(corpoEmailCaptor.getValue().contains(tokenAtivacao));
        assertEquals(usuarioCriado, resultado);
    }

    @Test
    void naoDeveCadastrarUsuarioQuandoEmailJaExistir() {
        CadastroUsuarioDTO dto = AuthDTOFactory.cadastroDTO();

        doThrow(new AuthEmailJaCadastradoException())
                .when(authValidator)
                .validarSeEmailOuCpfExistem(dto.email(), dto.cpf());

        AuthEmailJaCadastradoException exception = assertThrows(AuthEmailJaCadastradoException.class,
                () -> service.cadastrarUsuario(dto));

        assertEquals("E-mail já cadastrado.", exception.getMessage());
        verify(authValidator).validarSeEmailOuCpfExistem(dto.email(), dto.cpf());
        verifyNoInteractions(authFactory, usuarioRepository, jwtUtil, emailService);
    }

    @Test
    void naoDeveCadastrarUsuarioQuandoCPFJaExistir() {
        CadastroUsuarioDTO dto = AuthDTOFactory.cadastroDTO();

        doThrow(new AuthCPFJaCadastradoException())
                .when(authValidator)
                .validarSeEmailOuCpfExistem(dto.email(), dto.cpf());

        AuthCPFJaCadastradoException exception = assertThrows(AuthCPFJaCadastradoException.class,
                () -> service.cadastrarUsuario(dto));

        assertEquals("CPF já cadastrado.", exception.getMessage());
        verify(authValidator).validarSeEmailOuCpfExistem(dto.email(), dto.cpf());
        verifyNoInteractions(authFactory, usuarioRepository, jwtUtil, emailService);
    }

    @Test
    void deveConfirmarCadastroComSucesso() {
        String token = "token-valido";
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioNaoConfirmado();

        when(jwtUtil.extrairEmail(token)).thenReturn(usuario.getEmail());
        when(authValidator.validarEmail(usuario.getEmail())).thenReturn(usuario);
        when(jwtUtil.isTokenValido(token, usuario)).thenReturn(true);

        String resultado = service.confirmarCadastro(token);

        assertEquals("Conta confirmada com sucesso!", resultado);
        assertTrue(usuario.isAtivo());
    }

    @Test
    void deveLancarExcecaoQuandoEmailInvalido() {
        String token = "token-123";

        when(jwtUtil.extrairEmail(token)).thenReturn("email@naovalido.com");
        when(authValidator.validarEmail("email@naovalido.com"))
                .thenThrow(new AuthDadosInvalidosException());

        AuthDadosInvalidosException exception = assertThrows(AuthDadosInvalidosException.class,
                () -> service.confirmarCadastro(token));

        assertEquals("E-mail ou senha inválidos.", exception.getMessage());
    }

    @Test
    void naoDeveConfirmarCadastroQuandoTokenInvalido() {
        String token = "token-123";
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioNaoConfirmado();

        when(jwtUtil.extrairEmail(token)).thenReturn(usuario.getEmail());
        when(authValidator.validarEmail(usuario.getEmail())).thenReturn(usuario);
        when(jwtUtil.isTokenValido(token, usuario)).thenReturn(false);

        String resultado = service.confirmarCadastro(token);

        assertFalse(usuario.isAtivo());
    }

    @Test
    void naoDeveConfirmarCadastroQuandoUsuarioJaEstiverAtivo() {
        String token = "token-valido";
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();

        when(jwtUtil.extrairEmail(token)).thenReturn(usuario.getEmail());
        when(authValidator.validarEmail(usuario.getEmail())).thenReturn(usuario);
        when(jwtUtil.isTokenValido(token, usuario)).thenReturn(true);

        String resultado = service.confirmarCadastro(token);

        assertEquals("Usuário já confirmado.", resultado);
        assertTrue(usuario.isAtivo());
    }

    @Test
    void deveLogarUsuarioComSucesso() {
        LoginDTO dto = AuthDTOFactory.loginDTO();
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();
        String tokenGerado = "token-valido";

        when(authValidator.validarEmail(dto.email())).thenReturn(usuario);
        doNothing().when(authValidator).validarSenha(dto.senha(), usuario.getPassword());
        doNothing().when(authValidator).validarSeUsuarioConfirmado(usuario);
        when(jwtUtil.gerarToken(usuario)).thenReturn(tokenGerado);

        TokenDTO resultado = service.login(dto);

        assertEquals(tokenGerado, resultado.token());
    }

    @Test
    void naoDeveLogarQuandoEmailNaoEncontrado() {
        LoginDTO dto = AuthDTOFactory.loginDTO();

        when(authValidator.validarEmail(dto.email())).thenThrow(new AuthDadosInvalidosException());

        AuthDadosInvalidosException exception = assertThrows(AuthDadosInvalidosException.class,
                () -> service.login(dto));

        assertEquals("E-mail ou senha inválidos.", exception.getMessage());
        verify(authValidator).validarEmail(dto.email());
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void naoDeveLogarQuandoSenhaInvalida() {
        LoginDTO dto = AuthDTOFactory.loginDTO();
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioConfirmado();

        when(authValidator.validarEmail(dto.email())).thenReturn(usuario);
        doThrow(new AuthDadosInvalidosException())
                .when(authValidator)
                .validarSenha(dto.senha(), usuario.getPassword());;

        AuthDadosInvalidosException exception = assertThrows(AuthDadosInvalidosException.class,
                () -> service.login(dto));

        assertEquals("E-mail ou senha inválidos.", exception.getMessage());
        verify(authValidator).validarEmail(dto.email());
        verify(authValidator).validarSenha(dto.senha(), usuario.getPassword());
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void naoDeveLogarSeUsuarioNaoEstiverConfirmado() {
        LoginDTO dto = AuthDTOFactory.loginDTO();
        Usuario usuario = UsuarioFactoryTeste.criarUsuarioNaoConfirmado();

        when(authValidator.validarEmail(dto.email())).thenReturn(usuario);
        doNothing().when(authValidator).validarSenha(dto.senha(), usuario.getPassword());
        doThrow(new UsuarioNaoConfirmadoException())
                .when(authValidator)
                .validarSeUsuarioConfirmado(usuario);

        UsuarioNaoConfirmadoException exception = assertThrows(UsuarioNaoConfirmadoException.class,
                () -> service.login(dto));

        assertEquals("Usuário não confirmado, verifique e-mail.", exception.getMessage());
        verify(authValidator).validarEmail(dto.email());
        verify(authValidator).validarSenha(dto.senha(), usuario.getPassword());
        verify(authValidator).validarSeUsuarioConfirmado(usuario);
        verifyNoInteractions(jwtUtil);
    }
}
