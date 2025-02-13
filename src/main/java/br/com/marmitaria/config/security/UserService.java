package br.com.marmitaria.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.marmitaria.entity.usuario.Usuario;

public class UserService {
	public static Usuario authenticated() {
		try {
			return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
