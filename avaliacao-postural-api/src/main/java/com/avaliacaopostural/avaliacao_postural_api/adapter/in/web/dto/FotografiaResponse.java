package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDateTime;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;

public record FotografiaResponse(
    Long id,
    Long avaliacaoId,
    String contentType,
    LocalDateTime dataUpload,
    String urlArquivo
) {
    public static FotografiaResponse from(Fotografia f){
        String url = "/api/avaliacoes/" + f.getAvaliacaoId() + "/fotografias/" + f.getId() + "/arquivo";
        return new FotografiaResponse(f.getId(), f.getAvaliacaoId(), f.getContentType(), f.getDataUpload(), url);
    }
}
