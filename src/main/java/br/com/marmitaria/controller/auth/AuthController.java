package br.com.marmitaria.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.dto.security.LoginRequestDTO;
import br.com.marmitaria.dto.security.LoginResponseDTO;
import br.com.marmitaria.service.auth.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO LoginRequest) {
        LoginResponseDTO response = authService.autenticar(LoginRequest);
        return ResponseEntity.ok(response);
    }
}
