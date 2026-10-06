package com.avaliacaopostural.avaliacao_postural_api.application.domain.exception;

public class GrupoMuscularNaoEncontradoException extends RuntimeException{
    public GrupoMuscularNaoEncontradoException(Long id){
        super("Grupo muscular não encontrado: " + id);
    }
}
