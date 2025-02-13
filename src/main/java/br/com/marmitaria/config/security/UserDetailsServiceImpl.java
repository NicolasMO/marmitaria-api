package br.com.marmitaria.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UsuarioRepository usuarioRepository;
	
	public UserDetailsServiceImpl (UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encotrado."));
	}
}
