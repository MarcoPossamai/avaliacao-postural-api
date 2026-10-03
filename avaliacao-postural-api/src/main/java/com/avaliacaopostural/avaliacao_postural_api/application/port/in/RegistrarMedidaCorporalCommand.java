package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;


public record RegistrarMedidaCorporalCommand(
    Long avaliacaoId,
    Double peso,
    Double altura,
    Double percentualGordura,
    List<Circunferencia> circunferencias
) {
    public record Circunferencia(
        String tipo, 
        Double valorCm
    ){}
}
