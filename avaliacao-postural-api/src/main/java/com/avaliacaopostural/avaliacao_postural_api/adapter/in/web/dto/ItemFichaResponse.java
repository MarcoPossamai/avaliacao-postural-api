package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ItemFicha;

public record ItemFichaResponse(
    Long id,
    Long exercicioId,
    int ordem,
    int series,
    int repeticoesMin,
    int repeticoesMax,
    int descanso
) {
    public static ItemFichaResponse from(ItemFicha item){
        return new ItemFichaResponse(item.getId(), item.getExercicioId(), item.getOrdem(), 
            item.getSeries(), item.getRepeticoesMin(), item.getRepeticoesMax(), item.getDescanso());
    }
}
