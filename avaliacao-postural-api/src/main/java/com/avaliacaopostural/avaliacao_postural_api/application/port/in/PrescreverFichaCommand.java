package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;
import java.util.List;

public record PrescreverFichaCommand(
    Long alunoId,
    Long instrutorId,
    LocalDate dataInicio,
    LocalDate dataFim,
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
