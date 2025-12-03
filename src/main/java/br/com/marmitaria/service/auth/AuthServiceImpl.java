package br.com.marmitaria.service.auth;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.auth.TokenDTO;
import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
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
    public Usuario cadastrarUsuario(CadastroUsuarioDTO dto) {
        contexto.getAuthValidator().validarSeEmailOuCpfExistem(dto.email(), dto.cpf());
        Usuario usuario = contexto.getAuthFactory().criarUsuario(dto);

        String assunto = "Confirme seu cadastro";
        String mensagem = "Obrigado por se cadastrar!\n\n" +
                "Clique no link para confirmar sua conta:\n" +
                "http://localhost:8080/auth/confirmar?email=" + usuario.getEmail();

        contexto.getEmailService().enviarEmail(usuario.getEmail(), assunto, mensagem);

        return contexto.getUsuarioRepository().save(usuario);
    }

    @Override
    @Transactional
    public String confirmarCadastro(String email) {
        Usuario usuario = contexto.getAuthValidator().validarEmail(email);
        if (usuario.isEnabled()) return "Usuário já confirmado.";

        usuario.setAtivo(true);
        contexto.getUsuarioRepository().save(usuario);

        return "Conta confirmada com sucesso!";
    }
}
