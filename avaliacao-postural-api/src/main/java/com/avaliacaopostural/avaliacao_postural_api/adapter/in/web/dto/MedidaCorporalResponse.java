package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;

public record MedidaCorporalResponse(
    Long id,
    Long avaliacaoId,
    Double peso, 
    Double altura, 
    Double imc,
    Double percentualGordura,
    List<Circunferencia> circunferencias
) {
    
    public record Circunferencia(
        String tipo, 
        Double valorCm
    ){}

    public static MedidaCorporalResponse from(MedidaCorporal medida){
        List<Circunferencia> circunferencias = medida.getCircunferencias().stream()
            .map(c -> new Circunferencia(c.getTipo(), c.getValorCm()))
            .toList();

        return new MedidaCorporalResponse(medida.getId(), medida.getAvaliacaoId(), 
            medida.getPeso(), medida.getAltura(), medida.getImc(), 
            medida.getPercentualGordura(), circunferencias);
    }
}
