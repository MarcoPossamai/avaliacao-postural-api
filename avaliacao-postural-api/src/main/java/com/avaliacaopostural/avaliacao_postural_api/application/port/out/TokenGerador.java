package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Perfil;

public interface TokenGerador {
    
    String gerar(Long usuarioId, String email, Perfil perfil);
}
