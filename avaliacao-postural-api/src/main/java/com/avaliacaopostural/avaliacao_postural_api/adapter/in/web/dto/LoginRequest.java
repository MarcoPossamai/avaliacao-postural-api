package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AutenticarCommand;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank String email,
    @NotBlank String senha
) {

    public AutenticarCommand toCommand(){
        return new AutenticarCommand(email, senha);
    }
}
