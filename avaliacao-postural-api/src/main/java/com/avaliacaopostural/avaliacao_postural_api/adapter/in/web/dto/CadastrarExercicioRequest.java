package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarExercicioCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarExercicioRequest(
    @NotBlank String nome,
    String descricao,
    String urlVideo,
    @NotNull Long grupoMuscularId
) {
    public CadastrarExercicioCommand toCommand(){
        return new CadastrarExercicioCommand(nome, descricao, urlVideo, grupoMuscularId);
    }
}
