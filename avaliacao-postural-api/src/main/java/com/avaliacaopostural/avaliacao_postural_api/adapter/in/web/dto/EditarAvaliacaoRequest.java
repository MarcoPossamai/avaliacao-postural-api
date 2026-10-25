package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarAvaliacaoCommand;

public record EditarAvaliacaoRequest(
    @NotNull LocalDate dataAvaliacao,
    String observacoes
) {
    public EditarAvaliacaoCommand toCommand(Long avaliacaoId){
        return new EditarAvaliacaoCommand(avaliacaoId, dataAvaliacao, observacoes);
    }
}
