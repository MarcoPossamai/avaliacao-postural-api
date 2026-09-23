package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Perfil;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AutenticacaoResult;

public record LoginResponse(
    String token,
    String tipo,
    Perfil perfil,
    String nome
) {

    public static LoginResponse from(AutenticacaoResult resultado){
        return new LoginResponse(resultado.token(), "Bearer", resultado.perfil(), resultado.nome());
    }
}
