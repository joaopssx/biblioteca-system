package com.biblioteca.exception;

import com.biblioteca.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(com.biblioteca.exception.RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiResponse<Void>> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException e) {
        log.warn("Recurso não encontrado: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.erro(e.getMessage()));
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ApiResponse<Void>> handleRegraDeNegocioException(RegraDeNegocioException e) {
        log.warn("Regra de negócio violada: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.erro(e.getMessage()));
    }

    @ExceptionHandler(BibliotecaException.class)
    public ResponseEntity<ApiResponse<Void>> handleBibliotecaException(BibliotecaException e) {
        log.warn("Exceção da biblioteca: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.erro(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("Erro interno do servidor", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.erro("Erro interno do servidor"));
    }
}
