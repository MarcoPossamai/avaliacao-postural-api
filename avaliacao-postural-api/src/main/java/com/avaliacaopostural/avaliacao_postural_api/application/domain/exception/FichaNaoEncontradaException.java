package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class FichaNaoEncontradaException extends RuntimeException{
    public FichaNaoEncontradaException(Long id){
        super("Ficha não encontrada: " + id);
    }
}
