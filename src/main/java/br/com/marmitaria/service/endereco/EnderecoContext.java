package br.com.marmitaria.service.endereco;

import br.com.marmitaria.config.security.AuthenticatedUser;
import br.com.marmitaria.external.viacep.service.ViaCepService;
import br.com.marmitaria.repository.endereco.EnderecoRepository;
import br.com.marmitaria.service.endereco.factory.EnderecoFactory;
import br.com.marmitaria.service.endereco.mapper.EnderecoMapper;
import br.com.marmitaria.service.endereco.validator.EnderecoValidator;
import br.com.marmitaria.service.usuario.validator.UsuarioValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class EnderecoContext {

    private final AuthenticatedUser authenticatedUser;
    private final EnderecoRepository enderecoRepository;
    private final UsuarioValidator usuarioValidator;
    private final EnderecoValidator enderecoValidator;
    private final EnderecoFactory enderecoFactory;
    private final EnderecoMapper enderecoMapper;
    private final ViaCepService viaCepService;

}
