package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;

public record AvaliacaoResponse(
    Long id,
    Long alunoId,
    LocalDate dataAvaliacao,
    String observacoes
) {
    public static AvaliacaoResponse from(Avaliacao avaliacao){
        return new AvaliacaoResponse(avaliacao.getId(), avaliacao.getAlunoId(), 
        avaliacao.getDataAvaliacao(), avaliacao.getObservacoes());
    }
}
