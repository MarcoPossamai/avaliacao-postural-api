package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Exercicio;

public record ExercicioResponse(
    Long id,
    String nome,
    String descricao,
    String urlVideo,
    Long grupoMuscularId
) {
    public static ExercicioResponse from(Exercicio exercicio){
        return new ExercicioResponse(exercicio.getId(), exercicio.getNome(), exercicio.getDescricao(), exercicio.getUrlVideo(), exercicio.getGrupoMuscularId());
    }
}
