package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;
import java.util.List;

public record EditarFichaCommand(
        Long fichaId,
        String nome,
        LocalDate dataFim,
        Integer frequenciaSemanal,
        Long avaliacaoReferenciaId,
        List<Item> itens) {
            
    public record Item(
            Long id,
            Long exercicioId,
            int ordem,
            int series,
            int repeticoesMin,
            int repeticoesMax,
            int descanso) {
    }

}
