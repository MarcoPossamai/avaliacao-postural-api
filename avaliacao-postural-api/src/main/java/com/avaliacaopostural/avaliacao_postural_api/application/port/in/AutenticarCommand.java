package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

public record AutenticarCommand(
    String email,
    String senha
) {}
