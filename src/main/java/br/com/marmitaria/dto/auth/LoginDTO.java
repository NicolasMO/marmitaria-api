package br.com.marmitaria.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
   @NotBlank
   String email,

   @NotBlank
   String senha
) {}
