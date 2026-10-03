package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class ComparacaoIvalidaException extends RuntimeException{
    public ComparacaoIvalidaException(String motivo){
        super("Comparação inválida: " + motivo);
    }
}
