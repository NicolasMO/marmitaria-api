package br.com.marmitaria.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record CadastrarUsuarioDTO(
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
        @Size(min = 6, message = "Senha curta, mínimo de 6 caracteres")
        @Size(max = 20, message = "Senha com máximo de 20 caracteres")
        String senha
) {}