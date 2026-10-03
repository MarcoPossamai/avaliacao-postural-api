package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class DesvioNaoEncontradoException extends RuntimeException{
    public DesvioNaoEncontradoException(Long id){
        super("Desvio postural não encontrado: " + id);
    }
}
