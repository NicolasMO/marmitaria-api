package br.com.marmitaria.controller.auth;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.auth.TokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marmitaria.service.auth.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO dto) {
        TokenDTO token = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
