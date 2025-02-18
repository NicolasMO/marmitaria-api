package br.com.marmitaria.config.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marmitaria.entity.usuario.Usuario;
import br.com.marmitaria.repository.usuario.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UsuarioRepository usuarioRepository;
	
	public UserDetailsServiceImpl (UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	 @Override
	 @Transactional(readOnly = true)
	 public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	     Usuario usuario = usuarioRepository.findByEmail(email)
	             .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

	     return new org.springframework.security.core.userdetails.User(
	             usuario.getEmail(),
	             usuario.getSenha(),
	             new ArrayList<>()
	     );
	 }
}
