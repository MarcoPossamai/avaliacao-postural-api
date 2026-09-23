package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.CredenciaisInvalidasException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.EmailJaCadastradoException;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    
    @ExceptionHandler (EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> emailDuplicado(EmailJaCadastradoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validacao(MethodArgumentNotValidException ex){
        Map<String, String> erros = ex.getBindingResult().getFieldErrors().stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (primeiro, segundo) -> primeiro));
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler (CredenciaisInvalidasException.class)
    public ResponseEntity<Map<String, String>> credenciaisInvalidas(CredenciaisInvalidasException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", ex.getMessage()));
    }
}
