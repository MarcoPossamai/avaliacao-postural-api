package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class AvaliacaoNaoEncontradaException extends RuntimeException{
    
    public AvaliacaoNaoEncontradaException(Long id){
        super("Avaliação não encontrada: " + id);
    }
}
