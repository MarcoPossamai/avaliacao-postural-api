package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditarDesvioPosturalRequest(
    @NotNull RegiaoCorporal regiao,
    @NotBlank String tipo,
    @NotNull Severidade severidade
) {
    
}
