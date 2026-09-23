package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

public interface AutenticarUseCase {
    
    AutenticacaoResult autenticar(AutenticarCommand command);
}
