package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class AlunoNaoEncontradoException extends RuntimeException{
    
    public AlunoNaoEncontradoException(Long id){
        super("Aluno não encontrado: " + id);
    }
}
