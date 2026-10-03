package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;

public interface AdicionarFotografiaUseCase {
    Fotografia adicionar(AdicionarFotografiaCommand command);
}
