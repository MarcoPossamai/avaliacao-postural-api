package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import lombok.Getter;

@Getter 
public class GrupoMuscular {
    private final Long id;
    private final String nome; 
    private final String descricao;

    public GrupoMuscular(Long id, String nome, String descricao){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
}
