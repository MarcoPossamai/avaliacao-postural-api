package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarMedidaCorporalCommand;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RegistrarMedidaCorporalRequest(
    @NotNull @Positive Double peso,
    @NotNull @Positive Double altura,
    @NotNull @Size (min = 18, max = 18) List<@Valid Circunferencia> circunferencias,
    @NotNull @Size (min = 9, max = 9) List<@Valid DobraCutanea> dobrasCutaneas
) {
    
    public record Circunferencia(
        @NotBlank String tipo,
        @NotNull @Positive Double valorCm
    ){}

    public record DobraCutanea(
        @NotBlank String tipo,
        @NotNull @Positive Double valorMm
    ){}


    public RegistrarMedidaCorporalCommand toCommand(Long avaliacaoId){
        List<RegistrarMedidaCorporalCommand.Circunferencia> convertidas = circunferencias == null
            ? List.of() : circunferencias.stream().map(c -> new RegistrarMedidaCorporalCommand.Circunferencia(c.tipo(), c.valorCm()))
            .toList();

        List<RegistrarMedidaCorporalCommand.DobraCutanea> dobras = dobrasCutaneas == null
            ? List.of() : dobrasCutaneas.stream().map(d -> new RegistrarMedidaCorporalCommand.DobraCutanea(d.tipo(), d.valorMm()))
            .toList();
        
        return new RegistrarMedidaCorporalCommand(avaliacaoId, peso, altura, convertidas, dobras);
    }
}
