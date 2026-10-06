package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.time.LocalDate;

import lombok.Getter;

@Getter 
public class Avaliacao {
    
    private final Long id;
    private final Long alunoId;
    private final LocalDate dataAvaliacao;
    private final String observacoes;

    public Avaliacao(Long id, Long alunoId, LocalDate data, String observacoes){
        this.id = id;
        this.alunoId = alunoId;
        this.dataAvaliacao = data;
        this.observacoes = observacoes;
    }
}
