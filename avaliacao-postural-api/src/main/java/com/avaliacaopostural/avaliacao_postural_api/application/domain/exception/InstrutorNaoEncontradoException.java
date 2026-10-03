package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class InstrutorNaoEncontradoException extends RuntimeException{
    public InstrutorNaoEncontradoException(Long id){
        super("Instrutor não encontrado: " + id);
    }
}
