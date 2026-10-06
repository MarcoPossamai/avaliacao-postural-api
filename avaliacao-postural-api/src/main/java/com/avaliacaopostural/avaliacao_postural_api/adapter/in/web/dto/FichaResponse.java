package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;

public record FichaResponse(
    Long id,
    Long alunoId,
    Long instrutorId,
    LocalDate dataInicio,
    LocalDate dataFim,
    List<ItemFichaResponse> itens
) {
    public static FichaResponse from(Ficha ficha){
        return new FichaResponse(ficha.getId(), ficha.getAlunoId(), ficha.getInstrutorId(), 
            ficha.getDataInicio(), ficha.getDataFim(), ficha.getItens().stream().map(ItemFichaResponse::from).toList());
    }
}
