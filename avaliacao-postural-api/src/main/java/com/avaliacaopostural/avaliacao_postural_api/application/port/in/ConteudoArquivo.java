package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

public record ConteudoArquivo(
    byte[] bytes,
    String contentType
) {}
