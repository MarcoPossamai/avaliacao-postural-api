package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;

public interface PrescreverFichaUseCase {
    Ficha prescrever(PrescreverFichaCommand command);
}
