package br.com.marmitaria.service.auth;

import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.factory.AuthDTOFactory;
import br.com.marmitaria.factory.UsuarioFactory;
import br.com.marmitaria.service.auth.factory.AuthFactory;
import br.com.marmitaria.service.auth.validator.AuthValidator;
import br.com.marmitaria.service.email.EmailService;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import br.com.marmitaria.config.security.JwtUtil;

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
    @Mock private JwtUtil jwtUtil;
    @Mock private EmailService emailService;

    @InjectMocks
    private AuthServiceImpl service;

    private void mockDependencies() {
        when(contexto.getAuthValidator()).thenReturn(authValidator);
        when(contexto.getAuthFactory()).thenReturn(authFactory);
        when(contexto.getUsuarioRepository()).thenReturn(usuarioRepository);
        when(contexto.getJwtUtil()).thenReturn(jwtUtil);
        when(contexto.getEmailService()).thenReturn(emailService);
    }
/*
    @Test
    void deveCadastrarUsuarioComSucesso() {
        CadastroUsuarioDTO dto = AuthDTOFactory.cadastroValido();
        Usuario usuarioCriado = UsuarioFactory.criarUsuarioNaoConfirmado();
        Usuario usuarioSalvo = UsuarioFactory.criarUsuarioNaoConfirmado();
        String tokenAtivacao = "token-123";

        mockDependencies();
        when(authFactory.criarUsuario(dto)).thenReturn(usuarioCriado);
        when(usuarioRepository.save(usuarioCriado)).thenReturn(usuarioSalvo);
        when(jwtUtil.gerarTokenDeAtivacao(usuarioSalvo)).thenReturn(tokenAtivacao);

        ArgumentCaptor<String> corpoEmailCaptor = ArgumentCaptor.forClass(String.class);

        RespostaUsuarioDTO resultado = service.cadastrarUsuario(dto);

        InOrder inOrder = inOrder(authValidator, authFactory, usuarioRepository, jwtUtil, emailService);
        inOrder.verify(authValidator).validarSeEmailOuCpfExistem(dto.email(), dto.cpf());
        inOrder.verify(authFactory).criarUsuario(dto);
        inOrder.verify(usuarioRepository).save(usuarioCriado);
        inOrder.verify(jwtUtil).gerarTokenDeAtivacao(usuarioSalvo);
        inOrder.verify(emailService).enviarEmail(eq(dto.email()), anyString(), corpoEmailCaptor.capture());

        assertTrue(corpoEmailCaptor.getValue().contains(tokenAtivacao));
        assertEquals(usuarioSalvo, resultado);
    }*/

}
