package br.com.marmitaria.service.auth;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.auth.TokenDTO;
import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import br.com.marmitaria.exception.auth.AuthCPFJaCadastradoException;
import br.com.marmitaria.exception.auth.AuthDadosInvalidosException;
import br.com.marmitaria.exception.auth.AuthEmailJaCadastradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.marmitaria.config.security.JwtUtil;
import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthContext contexto;

    @Override
    public TokenDTO login(LoginDTO dto) {
        Usuario usuario = contexto.authValidator.validarEmail(dto.email());
        contexto.authValidator.validarSenha(dto.senha(), usuario.getPassword());
        String token = contexto.jwtUtil.gerarToken(usuario);

        return new TokenDTO(token);
    }

    @Override
    @Transactional
    public Usuario cadastrarUsuario(CadastroUsuarioDTO dto) {
        contexto.authValidator.verificarSeEmailOuCpfExistem(dto.email(), dto.cpf());
        Usuario usuario = contexto.authFactory.criarUsuario(dto);

        return contexto.usuarioRepository.save(usuario);
    }
}
