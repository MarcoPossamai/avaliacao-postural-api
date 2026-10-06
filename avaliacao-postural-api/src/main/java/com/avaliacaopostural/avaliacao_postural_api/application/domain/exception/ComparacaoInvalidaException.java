package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class ComparacaoInvalidaException extends RuntimeException{
    public ComparacaoInvalidaException(String motivo){
        super("Comparação inválida: " + motivo);
    }
}
