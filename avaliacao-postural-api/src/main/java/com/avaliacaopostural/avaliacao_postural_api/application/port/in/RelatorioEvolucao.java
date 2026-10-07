package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;

public record RelatorioEvolucao(
    List<PontoMedida> evolucaoMedidas,
    List<PontoDesvio> evolucaoDesvios,
    List<PontoCarga> evolucaoCargas
) {
    public record PontoMedida(
        LocalDate data,
        Double peso,
        Double imc,
        Double percentualGordura
    ){}

    public record PontoDesvio(
        LocalDate data, 
        RegiaoCorporal regiao, 
        String tipo,
        Severidade severidade
    ){}

    public record PontoCarga(
        LocalDateTime data, 
        Long itemFichaId,
        Double cargaUtilizada,
        Integer repeticoes
    ){}
}
