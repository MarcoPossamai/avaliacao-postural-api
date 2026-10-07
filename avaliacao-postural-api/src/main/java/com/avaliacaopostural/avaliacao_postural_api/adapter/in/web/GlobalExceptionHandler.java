package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AlunoNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ArquivoInvalidoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ComparacaoInvalidaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.CredenciaisInvalidasException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.DesvioNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.EmailJaCadastradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ExercicioNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.FichaNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.FotografiaNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.GrupoMuscularNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.InstrutorNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ItemFichaInvalidaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.LimiteDeFotografiasExcedidoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.MedidaJaRegistradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.PeriodoInvalidoException;

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

    @ExceptionHandler (AlunoNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> alunoNaoEncontrado(AlunoNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (AvaliacaoNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> avaliacaoNaoEncontrada(AvaliacaoNaoEncontradaException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (MedidaJaRegistradaException.class)
    public ResponseEntity<Map<String, String>> medidaJaRegistrada(MedidaJaRegistradaException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (LimiteDeFotografiasExcedidoException.class)
    public ResponseEntity<Map<String, String>> limiteExcedido(LimiteDeFotografiasExcedidoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (FotografiaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> fotografiaNaoEncontrada(FotografiaNaoEncontradaException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (ArquivoInvalidoException.class)
    public ResponseEntity<Map<String, String>> arquivoInvalido(ArquivoInvalidoException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (ComparacaoInvalidaException.class)
    public ResponseEntity<Map<String, String>> comparacaoInvalida(ComparacaoInvalidaException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (DesvioNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> desvioNaoEncontrado(DesvioNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (InstrutorNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> instrutorNaoEncontrado(InstrutorNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (ExercicioNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> exercicioNaoEncontrado(ExercicioNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (GrupoMuscularNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> grupoMuscularNaoEncontrado(GrupoMuscularNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (FichaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> fichaNaoEncontrada(FichaNaoEncontradaException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (ItemFichaInvalidaException.class)
    public ResponseEntity<Map<String, String>> itemFichaInvalido(ItemFichaInvalidaException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler (PeriodoInvalidoException.class)
    public ResponseEntity<Map<String, String>> periodoInvalido(PeriodoInvalidoException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", ex.getMessage()));
    }
}
