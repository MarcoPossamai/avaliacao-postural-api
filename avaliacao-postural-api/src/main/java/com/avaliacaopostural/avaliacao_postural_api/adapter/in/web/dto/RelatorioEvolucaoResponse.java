package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RelatorioEvolucao;

public record RelatorioEvolucaoResponse(
    List<PontoMedidaResponse> evolucaoMedidas,
    List<PontoDesvioResponse> evolucaoDesvios,
    List<PontoCargaResponse> evolucaoCargas
) {
    public record PontoMedidaResponse(
        LocalDate data,
        Double peso,
        Double imc,
        Double percentualGordura
    ){}

    public record PontoDesvioResponse(
        LocalDate data,
        RegiaoCorporal regiao,
        String tipo,
        Severidade severidade
    ){}

    public record PontoCargaResponse(
        LocalDateTime data,
        Long itemFichaId,
        Double cargaUtilizada,
        Integer repeticoes
    ){}

    public static RelatorioEvolucaoResponse from(RelatorioEvolucao relatorio){
        var medidas = relatorio.evolucaoMedidas().stream()
            .map(m -> new PontoMedidaResponse(m.data(), m.peso(), m.imc(), m.percentualGordura())).toList();
        var desvios = relatorio.evolucaoDesvios().stream()
            .map(d -> new PontoDesvioResponse(d.data(), d.regiao(), d.tipo(), d.severidade())).toList();
        var cargas = relatorio.evolucaoCargas().stream()
            .map(c -> new PontoCargaResponse(c.data(), c.itemFichaId(), c.cargaUtilizada(), c.repeticoes())).toList();
        return new RelatorioEvolucaoResponse(medidas, desvios, cargas);
    }
}
