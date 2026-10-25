package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;

public record FichaResponse(
    Long id,
    Long alunoId,
    Long instrutorId,
    String nome,
    LocalDate dataInicio,
    LocalDate dataFim,
    Integer frequenciaSemanal,
    Long avaliacaoReferenciaId,
    List<ItemFichaResponse> itens
) {
    public static FichaResponse from(Ficha ficha){
        return new FichaResponse(ficha.getId(), ficha.getAlunoId(), ficha.getInstrutorId(), ficha.getNome(),
            ficha.getDataInicio(), ficha.getDataFim(), ficha.getFrequenciaSemanal(), ficha.getAvaliacaoReferenciaId(),
            ficha.getItens().stream().map(ItemFichaResponse::from).toList());
    }
}