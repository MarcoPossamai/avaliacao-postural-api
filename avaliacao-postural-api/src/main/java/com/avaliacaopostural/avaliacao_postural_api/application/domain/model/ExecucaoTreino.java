package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ExecucaoTreino {
    private final Long id;
    private final Long alunoId;
    private final Long fichaId;
    private final Long itemFichaId;
    private final Long exercicioId;
    private final int numeroSerie;
    private final Double cargaUtilizada;
    private final Integer repeticoes;
    private final LocalDateTime horaInicio;
    private final LocalDateTime horaFim;
    private final String observacao;

    public ExecucaoTreino(Long id, Long alunoId, Long fichaId, Long itemFichaId, Long exercicioId, int numeroSerie,
            Double cargaUtilizada, Integer repeticoes, LocalDateTime horaInicio, LocalDateTime horaFim,
            String observacao) {
        this.id = id;
        this.alunoId = alunoId;
        this.fichaId = fichaId;
        this.itemFichaId = itemFichaId;
        this.exercicioId = exercicioId;
        this.numeroSerie = numeroSerie;
        this.cargaUtilizada = cargaUtilizada;
        this.repeticoes = repeticoes;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.observacao = observacao;
    }

}
