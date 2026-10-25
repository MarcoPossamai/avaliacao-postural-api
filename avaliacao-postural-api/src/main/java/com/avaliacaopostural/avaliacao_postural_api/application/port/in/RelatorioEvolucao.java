package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;

public record RelatorioEvolucao(
        List<PontoMedida> evolucaoMedidas,
        List<PontoCircunferencia> evolucaoCircunferencias,
        List<PontoDobra> evolucaoDobras,
        List<PontoDesvio> evolucaoDesvios,
        List<PontoCarga> evolucaoCargas) {
    public record PontoMedida(
            LocalDate data,
            Double peso,
            Double imc,
            Double percentualGordura) {
    }

    public record PontoCircunferencia(
            LocalDate data,
            String tipo,
            Double valorCm) {
    }

    public record PontoDobra(
            LocalDate data,
            String tipo,
            Double valorMm) {
    }

    public record PontoDesvio(
            LocalDate data,
            RegiaoCorporal regiao,
            String tipo,
            Severidade severidade) {
    }

    public record PontoCarga(
            LocalDateTime data,
            Long itemFichaId,
            Double cargaUtilizada,
            Integer repeticoes) {
    }
}
