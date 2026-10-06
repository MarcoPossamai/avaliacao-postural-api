package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.GrupoMuscular;

public record GrupoMuscularResponse(
    Long id,
    String nome,
    String descricao
) {
    public static GrupoMuscularResponse from(GrupoMuscular grupo){
        return new GrupoMuscularResponse(grupo.getId(), grupo.getNome(), grupo.getDescricao());
    }
}
