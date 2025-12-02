package br.com.marmitaria.exception.handler;

import br.com.marmitaria.dto.erro.RespostaErroAPI;
import br.com.marmitaria.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErroAPI> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        List<String> detalhes = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        RespostaErroAPI resposta = new RespostaErroAPI(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Erro de validação nos campos enviados",
                detalhes,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<RespostaErroAPI> handleBusinessException(
            BusinessException ex,
            HttpServletRequest request
    ) {
        RespostaErroAPI resposta = new RespostaErroAPI(
                ex.getStatus().value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                List.of(ex.getMessage()),
                request.getRequestURI()
        );

        return ResponseEntity.status(ex.getStatus()).body(resposta);
    }

}
