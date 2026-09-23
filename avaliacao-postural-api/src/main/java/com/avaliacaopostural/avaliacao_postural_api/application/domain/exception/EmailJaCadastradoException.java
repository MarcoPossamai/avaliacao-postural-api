package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class EmailJaCadastradoException extends RuntimeException{
    
    public EmailJaCadastradoException(String email){
        super("E-mail já cadastrado: " + email);
    }
}
