package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class ArquivoInvalidoException extends RuntimeException{
    public ArquivoInvalidoException(String motivo){
        super("Arquivo inválido: " + motivo);
    }
}
