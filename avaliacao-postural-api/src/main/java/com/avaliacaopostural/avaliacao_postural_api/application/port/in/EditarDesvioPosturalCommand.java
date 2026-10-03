package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;

public record EditarDesvioPosturalCommand(
    Long avaliacaoId,
    Long desvioId,
    RegiaoCorporal regiao,
    String tipo,
    Severidade severidade
) {}
