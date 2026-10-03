package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import lombok.Getter;

@Getter 
public class DesvioPostural {
    private final Long id;
    private final Long avaliacaoId;
    private final RegiaoCorporal regiao;
    private final String tipo;
    private final Severidade severidade;

    public DesvioPostural(Long id, Long avaliacaoId, RegiaoCorporal regiao, String tipo, Severidade severidade){
        this.id = id;
        this.avaliacaoId = avaliacaoId;
        this.regiao = regiao;
        this.tipo = tipo;
        this.severidade = severidade;
    }
}
