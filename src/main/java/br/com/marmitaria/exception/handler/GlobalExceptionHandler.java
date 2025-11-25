package br.com.marmitaria.exception.handler;

import br.com.marmitaria.dto.erro.RespostaErroAPI;
import br.com.marmitaria.exception.ingrediente.IngredienteJaExistenteException;
import br.com.marmitaria.exception.ingrediente.IngredienteNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

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

    @ExceptionHandler(IngredienteNaoEncontradoException.class)
    public ResponseEntity<RespostaErroAPI> handleNotFound(
            IngredienteNaoEncontradoException ex,
            HttpServletRequest request
    ) {
        RespostaErroAPI resposta = new RespostaErroAPI(
                HttpStatus.NOT_FOUND.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                List.of(ex.getMessage()),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    @ExceptionHandler(IngredienteJaExistenteException.class)
    public ResponseEntity<RespostaErroAPI> handleConflict(
            IngredienteJaExistenteException ex,
            HttpServletRequest request
    ) {
        RespostaErroAPI resposta = new RespostaErroAPI(
                HttpStatus.CONFLICT.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                List.of(ex.getMessage()),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

}
