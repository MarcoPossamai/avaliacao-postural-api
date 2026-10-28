package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDateTime;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ExecucaoTreino;

public record ExecucaoTreinoResponse(
        Long id,
        Long alunoId,
        Long fichaId,
        Long itemFichaId,
        Long exercicioId,
        int numeroSerie,
        Double cargaUtilizada,
        Integer repeticoes,
        LocalDateTime horaInicio,
        LocalDateTime horaFim,
        String observacao) {
            
    public static ExecucaoTreinoResponse from(ExecucaoTreino execucao) {
        return new ExecucaoTreinoResponse(execucao.getId(), execucao.getAlunoId(), execucao.getFichaId(),
                execucao.getItemFichaId(),
                execucao.getExercicioId(), execucao.getNumeroSerie(), execucao.getCargaUtilizada(),
                execucao.getRepeticoes(), execucao.getHoraInicio(), execucao.getHoraFim(), execucao.getObservacao());
    }
}
