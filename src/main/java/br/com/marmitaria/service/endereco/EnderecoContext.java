package br.com.marmitaria.service.endereco;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.external.viacep.service.ViaCepService;
import br.com.marmitaria.repository.endereco.EnderecoRepository;
import br.com.marmitaria.service.endereco.factory.EnderecoFactory;
import br.com.marmitaria.service.endereco.mapper.EnderecoMapper;
import br.com.marmitaria.service.endereco.validator.EnderecoValidator;
import br.com.marmitaria.service.usuario.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnderecoContext {

    public final AuthenticatedUser authenticatedUser;
    public final EnderecoRepository enderecoRepository;
    public final UsuarioValidator usuarioValidator;
    public final EnderecoValidator enderecoValidator;
    public final EnderecoFactory enderecoFactory;
    public final EnderecoMapper enderecoMapper;
    public final ViaCepService viaCepService;

}
