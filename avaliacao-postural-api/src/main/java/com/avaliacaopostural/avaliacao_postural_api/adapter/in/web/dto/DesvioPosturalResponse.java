package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;

public record DesvioPosturalResponse(
    Long id, 
    Long avaliacaoId,
    RegiaoCorporal regiao,
    String tipo,
    Severidade severidade
) {
    public static DesvioPosturalResponse from(DesvioPostural d){
        return new DesvioPosturalResponse(d.getId(), d.getAvaliacaoId(), d.getRegiao(), d.getTipo(), d.getSeveridade());
    }
}
