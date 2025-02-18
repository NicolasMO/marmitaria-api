package br.com.marmitaria.service.auth;

import br.com.marmitaria.dto.security.LoginRequestDTO;
import br.com.marmitaria.dto.security.LoginResponseDTO;

public interface AuthService {
	LoginResponseDTO autenticar(LoginRequestDTO LoginRequest);
}