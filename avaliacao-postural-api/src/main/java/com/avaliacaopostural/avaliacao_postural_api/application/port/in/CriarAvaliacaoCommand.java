package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;

public record CriarAvaliacaoCommand(
    Long alunoId,
    LocalDate dataAvaliacao,
    String obervacoes
) {}
