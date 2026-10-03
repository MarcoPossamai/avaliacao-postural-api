package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record CriarAvaliacaoRequest(
    @NotNull LocalDate dataAvaliacao,
    String observacoes
) {}
