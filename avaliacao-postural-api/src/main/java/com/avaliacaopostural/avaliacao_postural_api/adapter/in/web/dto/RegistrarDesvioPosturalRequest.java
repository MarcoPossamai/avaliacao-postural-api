package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarDesvioPosturalCommand;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegistrarDesvioPosturalRequest(
    @NotEmpty @Valid List<@Valid Item> desvios
) {
    public record Item(
        @NotNull RegiaoCorporal regiao,
        @NotBlank String tipo,
        @NotNull Severidade severidade
    ){}

    public RegistrarDesvioPosturalCommand toCommand(Long avaliacaoId){
        var itens = desvios.stream()
            .map(i -> new RegistrarDesvioPosturalCommand.Item(i.regiao(), i.tipo(), i.severidade()))
            .toList();
        return new RegistrarDesvioPosturalCommand(avaliacaoId, itens);
    }
}
