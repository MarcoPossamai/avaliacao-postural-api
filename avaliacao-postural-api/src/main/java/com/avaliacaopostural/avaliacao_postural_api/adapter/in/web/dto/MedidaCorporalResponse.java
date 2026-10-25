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
    List<Circunferencia> circunferencias,
    List<DobraCutanea> dobraCutaneas
) {
    
    public record Circunferencia(
        String tipo, 
        Double valorCm
    ){}

    public record DobraCutanea(
        String tipo,
        Double valorMm
    ){}

    public static MedidaCorporalResponse from(MedidaCorporal medida){
        List<Circunferencia> circunferencias = medida.getCircunferencias().stream()
            .map(c -> new Circunferencia(c.getTipo(), c.getValorCm()))
            .toList();

        List<DobraCutanea> dobras = medida.getDobrasCutaneas().stream()
            .map(d -> new DobraCutanea(d.getTipo(), d.getValorMm()))
            .toList();

        return new MedidaCorporalResponse(medida.getId(), medida.getAvaliacaoId(), 
            medida.getPeso(), medida.getAltura(), medida.getImc(), 
            medida.getPercentualGordura(), circunferencias, dobras);
    }
}
