package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class MedidaJaRegistradaException extends RuntimeException{
    
    public MedidaJaRegistradaException(Long avaliacaoId){
        super("A avaliação " + avaliacaoId + " já possui medidas corporais registradas");
    }
}
