package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;

public record RegistrarDesvioPosturalCommand(
    Long avaliacaoId,
    List<Item> desvios
) {
    public record Item(
        RegiaoCorporal regiao, 
        String tipo, 
        Severidade severidade
    ){}
}
