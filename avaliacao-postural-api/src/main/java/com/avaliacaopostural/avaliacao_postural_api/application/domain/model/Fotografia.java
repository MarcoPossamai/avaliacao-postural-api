package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter 
public class Fotografia {
    
    private final Long id;
    private final Long avaliacaoId;
    private final String caminhoArquivo;
    private final String contentType;
    private final LocalDateTime dataUpload;

    public Fotografia(Long id, Long avaliacaoId, String caminhoArquivo, String contentType, LocalDateTime dataUpload){
        this.id = id;
        this.avaliacaoId = avaliacaoId;
        this.caminhoArquivo = caminhoArquivo;
        this.contentType = contentType;
        this.dataUpload = dataUpload;
    }
}
