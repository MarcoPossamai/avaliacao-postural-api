package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDateTime;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarExecucaoCommand;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegistrarExecucaoRequest(
    @NotNull Long itemFichaId,
    @Positive int numeroSerie,
    Double cargaUtilizada,
    Integer repeticoes,
    @NotNull LocalDateTime horaInicio,
    LocalDateTime horaFim,
    String observacao
) {
    public RegistrarExecucaoCommand toCommand(Long fichaId, Long alunoId){
        return new RegistrarExecucaoCommand(fichaId, alunoId, itemFichaId, numeroSerie, cargaUtilizada, repeticoes, horaInicio, horaFim, observacao);
    }
}
