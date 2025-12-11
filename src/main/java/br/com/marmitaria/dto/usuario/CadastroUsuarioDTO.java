package br.com.marmitaria.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CadastroUsuarioDTO(
        @Pattern(
                regexp = "^[A-Za-zÀ-ÿ ]+$",
                message = "O nome deve conter apenas letras e espaços."
        )
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Email(message = "E-mail inválido")
        @NotBlank(message = "E-mail é obrigatório")
        String email,

        @CPF(message = "CPF inválido")
        @NotBlank(message = "CPF é obrigatório")
        String cpf,

        @NotBlank(message = "Celular é obrigatório")
        @Pattern(regexp = "\\+?\\d{10,11}", message = "Número de celular inválido.")
        String celular,

        @NotBlank(message = "Senha é obrigatória")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%])[A-Za-z\\d!@#$%]{6,20}$",
                message = "A senha deve ter 6–20 caracteres, contendo ao menos 1 letra maiúscula, 1 minúscula, 1 número e 1 símbolo (!@#$%)."
        )
        String senha
) {}