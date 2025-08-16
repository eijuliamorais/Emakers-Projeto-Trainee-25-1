package com.example.springboot.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Erro: Pessoa, Livro ou Empréstimo não encontrados
    @ExceptionHandler({ItemNaoEncontradoException.class, LivroJaEmprestadoException.class})
    public ResponseEntity<ErrorResponse> handleBusinessException(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        log.warn("Erro de negócio: {}", ex.getMessage());
        return buildErrorResponse(status, ex.getMessage(), request);
    }

    // Erros de validação (inputs inválidos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors()
                          .stream()
                          .map(err -> err.getField() + ": " + err.getDefaultMessage())
                          .reduce("", (s1, s2) -> s1 + "; " + s2);

        log.warn("Erro de validação: {}", errors);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errors, request);
    }

    // Erros genéricos (última linha de defesa)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        log.error("Erro inesperado: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                  "Erro interno inesperado. Tente novamente mais tarde.",
                                  request);
    }

    // Método auxiliar para formatar a resposta
    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            ((ServletWebRequest)request).getRequest().getRequestURI()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
        public ResponseEntity<ErrorResponse> handleEmailJaCadastradoException(EmailJaCadastradoException ex, WebRequest request) {
        log.warn("Erro de registro: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
    // Erro 401 - Não autenticado (login errado ou token inválido)
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorized(Exception ex, WebRequest request) {
        log.warn("Não autenticado: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Token ausente, inválido ou credenciais incorretas.", request);
    }

    // Erro 403 - Não autorizado (token válido mas sem permissão)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(AccessDeniedException ex, WebRequest request) {
        log.warn("Acesso negado: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este recurso.", request);
    }
}

