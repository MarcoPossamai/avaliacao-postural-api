package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.PrescreverFichaCommand;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PrescreverFichaRequest(
    @NotNull LocalDate dataInicio,
    LocalDate dataFim,
    @NotEmpty @Valid List<@Valid Item> itens
) {
    public record Item(
        @NotNull Long exercicioId,
        @Positive int ordem,
        @Positive int series,
        @Min (1) int repeticoesMin,
        @Min (1) int repeticoesMax,
        @Min (0) int descanso
    ){}

    public PrescreverFichaCommand toCommand(Long alunoId, Long instrutorId){
        List<PrescreverFichaCommand.Item> itensConvertidos = itens.stream()
            .map(i -> new PrescreverFichaCommand.Item(i.exercicioId, i.ordem, i.series, i.repeticoesMin, i.repeticoesMax, i.descanso)).toList();
        
        return new PrescreverFichaCommand(alunoId, instrutorId, dataInicio, dataFim, itensConvertidos);
    }
}
