package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarFichaCommand;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EditarFichaRequest(
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

    public EditarFichaCommand toCommand(Long fichaId){
        List<EditarFichaCommand.Item> itensConvertidos = itens.stream().map(i -> new EditarFichaCommand.Item(i.exercicioId, i.ordem, i.series, 
            i.repeticoesMin, i.repeticoesMax, i.descanso)).toList();
        return new EditarFichaCommand(fichaId, dataFim, itensConvertidos);
    }
}
