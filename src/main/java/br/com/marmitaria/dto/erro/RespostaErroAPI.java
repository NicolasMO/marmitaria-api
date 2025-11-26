package br.com.marmitaria.dto.erro;

import java.time.LocalDateTime;
import java.util.List;

public record RespostaErroAPI(
        int status,
        String error,
        String message,
        List<String> details,
        String path,
        LocalDateTime timestamp
) {
    public RespostaErroAPI(int status, String error, String message, List<String> details, String path) {
        this(status, error, message, details, path, LocalDateTime.now());
    }
}