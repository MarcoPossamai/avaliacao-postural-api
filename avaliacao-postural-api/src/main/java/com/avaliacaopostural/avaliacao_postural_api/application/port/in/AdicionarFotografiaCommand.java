package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

public record AdicionarFotografiaCommand(
    Long avaliacaoId,
    String nomeOriginal,
    String contentType, 
    byte[] conteudo
) {}
