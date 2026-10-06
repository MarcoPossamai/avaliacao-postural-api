package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class ItemFichaInvalidaException extends RuntimeException{
    public ItemFichaInvalidaException(String motivo){
        super(motivo);
    }
}
