package br.com.marmitaria.service.endereco;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.external.viacep.service.ViaCepService;
import br.com.marmitaria.repository.endereco.EnderecoRepository;
import br.com.marmitaria.repository.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnderecoContext {

    public final AuthenticatedUser authenticatedUser;
    public final EnderecoRepository enderecoRepository;
    public final UsuarioRepository usuarioRepository;
    public final ViaCepService viaCepService;

}
