package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class FotografiaNaoEncontradaException extends RuntimeException{
    public FotografiaNaoEncontradaException(Long id){
        super("Fotografia não encontrada: " + id);
    }
}
