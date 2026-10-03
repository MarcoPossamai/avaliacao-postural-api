package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.DetalheAvaliacao;

public record DetalheAvaliacaoResponse(
    AvaliacaoResponse avaliacao,
    MedidaCorporalResponse medidaCorporal,
    List<FotografiaResponse> fotografias,
    List<DesvioPosturalResponse> desvios
) {
    public static DetalheAvaliacaoResponse from(DetalheAvaliacao detalhe){
        return new DetalheAvaliacaoResponse(
            AvaliacaoResponse.from(detalhe.avaliacao()), 
            detalhe.medidaCorporal() != null ? MedidaCorporalResponse.from(detalhe.medidaCorporal()) : null, 
            detalhe.fotografias().stream().map(FotografiaResponse::from).toList(),
            detalhe.desvios().stream().map(DesvioPosturalResponse::from).toList()
        );
    }
}
