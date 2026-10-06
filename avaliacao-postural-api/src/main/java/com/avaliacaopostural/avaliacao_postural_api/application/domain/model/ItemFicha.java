package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ItemFichaInvalidaException;

import lombok.Getter;

@Getter 
public class ItemFicha {
    private final Long id;
    private final Long exercicioId;
    private final int ordem;
    private final int series;
    private final int repeticoesMin;
    private final int repeticoesMax;
    private final int descanso;

    public ItemFicha(Long id, Long exercicioId, int ordem, int series, int repeticoesMin, int repeticoesMax, int descanso){
        if (repeticoesMin > repeticoesMax) {
            throw new ItemFichaInvalidaException("repeticoes mínimas não podem ser maiores que as máximas");
        }
        this.id = id;
        this.exercicioId = exercicioId;
        this.ordem = ordem;
        this.series = series;
        this.repeticoesMin = repeticoesMin;
        this.repeticoesMax = repeticoesMax;
        this.descanso = descanso;
    }
}
