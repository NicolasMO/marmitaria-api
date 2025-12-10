package br.com.marmitaria.service.auth;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.auth.TokenDTO;
import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import br.com.marmitaria.dto.usuario.RespostaUsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.com.marmitaria.entity.usuario.Usuario;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthContext contexto;

    @Override
    public TokenDTO login(LoginDTO dto) {
        Usuario usuario = contexto.getAuthValidator().validarEmail(dto.email());
        contexto.getAuthValidator().validarSenha(dto.senha(), usuario.getPassword());
        contexto.getAuthValidator().validarSeUsuarioConfirmado(usuario);

        String token = contexto.getJwtUtil().gerarToken(usuario);

        return new TokenDTO(token);
    }

    @Override
    @Transactional
    public RespostaUsuarioDTO cadastrarUsuario(CadastroUsuarioDTO dto) {
        contexto.getAuthValidator().validarSeEmailOuCpfExistem(dto.email(), dto.cpf());
        Usuario usuario = contexto.getAuthFactory().criarUsuario(dto);
        Usuario salvo = contexto.getUsuarioRepository().save(usuario);

        enviarEmailDeConfirmacao(salvo, usuario);

        return contexto.getUsuarioMapper().paraDTO(usuario);
    }

    @Override
    @Transactional
    public String confirmarCadastro(String token) {
        String email = contexto.getJwtUtil().extrairEmail(token);
        Usuario usuario = contexto.getAuthValidator().validarEmail(email);

        if (!contexto.getJwtUtil().isTokenValido(token, usuario)) return "Token inválido ou expirado.";
        if (usuario.isEnabled()) return "Usuário já confirmado.";

        usuario.setAtivo(true);

        return "Conta confirmada com sucesso!";
    }

    private void enviarEmailDeConfirmacao(Usuario salvo, Usuario usuario) {
        String tokenAtivacao = contexto.getJwtUtil().gerarTokenDeAtivacao(salvo);

        String assunto = "Confirme seu cadastro";
        String mensagem = "Obrigado por se cadastrar!\n\n" +
                "Clique no link para confirmar sua conta:\n" +
                "http://localhost:8080/auth/confirmar?token=" + tokenAtivacao;

        contexto.getEmailService().enviarEmail(usuario.getEmail(), assunto, mensagem);
    }
}
