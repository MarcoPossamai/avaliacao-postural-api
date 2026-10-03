package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;

public interface CriarAvaliacaoUseCase {
    
    Avaliacao criar(CriarAvaliacaoCommand command);
}
