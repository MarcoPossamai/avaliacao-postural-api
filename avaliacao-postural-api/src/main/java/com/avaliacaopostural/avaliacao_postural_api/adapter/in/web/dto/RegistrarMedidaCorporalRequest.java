package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarMedidaCorporalCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RegistrarMedidaCorporalRequest(
    @NotNull @Positive Double peso,
    @NotNull @Positive Double altura,
    Double percentualGordura,
    List<Circunferencia> circunferencias
) {
    
    public record Circunferencia(
        @NotBlank String tipo,
        @NotNull Double valorCm
    ){}

    public RegistrarMedidaCorporalCommand toCommand(Long avaliacaoId){
        List<RegistrarMedidaCorporalCommand.Circunferencia> convertidas = circunferencias == null
            ? List.of() : circunferencias.stream().map(c -> new RegistrarMedidaCorporalCommand.Circunferencia(c.tipo(), c.valorCm()))
            .toList();
        
        return new RegistrarMedidaCorporalCommand(avaliacaoId, peso, altura, percentualGordura, convertidas);
    }
}
