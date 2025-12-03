package br.com.marmitaria.controller.auth;

import br.com.marmitaria.dto.auth.LoginDTO;
import br.com.marmitaria.dto.auth.TokenDTO;
import br.com.marmitaria.dto.usuario.CadastroUsuarioDTO;
import br.com.marmitaria.entity.usuario.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.marmitaria.service.auth.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody CadastroUsuarioDTO cadastroUsuarioDTO) {
        Usuario criado = authService.cadastrarUsuario(cadastroUsuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @GetMapping("/confirmar")
    public ResponseEntity<String> confirmarCadastro(@RequestParam String token) {
        String resposta = authService.confirmarCadastro(token);
        return ResponseEntity.status(HttpStatus.OK).body(resposta);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dto) {
        TokenDTO token = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
