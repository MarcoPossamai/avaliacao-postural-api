package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class ExercicioNaoEncontradoException extends RuntimeException{
    public ExercicioNaoEncontradoException(Long id){
        super("Exercício não encontrado: " + id);
    }
}
