package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Perfil;

public record AutenticacaoResult(
    String token,
    Perfil perfil,
    String nome
) {}
