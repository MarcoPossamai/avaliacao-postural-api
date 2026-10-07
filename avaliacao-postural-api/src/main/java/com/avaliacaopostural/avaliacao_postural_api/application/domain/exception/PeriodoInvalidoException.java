package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class PeriodoInvalidoException extends RuntimeException{
    public PeriodoInvalidoException(String motivo){
        super("Período inválido: " + motivo);
    }
}
