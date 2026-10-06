package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import lombok.Getter;

@Getter 
public class Exercicio {
    private final Long id;
    private final String nome;
    private final String descricao;
    private final String urlVideo;
    private final Long grupoMuscularId;

    public Exercicio(Long id, String nome, String descricao, String urlVideo, Long grupoMuscularId){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.urlVideo = urlVideo;
        this.grupoMuscularId = grupoMuscularId;
    }
}
