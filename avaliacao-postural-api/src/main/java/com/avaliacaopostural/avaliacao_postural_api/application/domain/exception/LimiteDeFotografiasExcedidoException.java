package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class LimiteDeFotografiasExcedidoException extends RuntimeException{
    public LimiteDeFotografiasExcedidoException(Long avaliacaoId){
        super("A avaliação " + avaliacaoId + " já atingiu o limite de 17 fotografias");
    }
}
