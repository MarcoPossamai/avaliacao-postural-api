package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;
import java.util.List;

public record PrescreverFichaCommand(
    Long alunoId,
    Long instrutorId,
    String nome,
    LocalDate dataInicio,
    LocalDate dataFim,
    Integer frequenciaSemanal,
    Long avaliacaoReferenciaId,
    List<Item> itens
) {
    public record Item(
        Long exercicioId,
        int ordem,
        int series,
        int repeticoesMin,
        int repeticoesMax,
        int descanso
    ){}
}

