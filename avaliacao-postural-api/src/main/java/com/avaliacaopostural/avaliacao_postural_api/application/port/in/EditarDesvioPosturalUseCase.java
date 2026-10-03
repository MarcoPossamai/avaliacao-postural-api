package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;

public interface EditarDesvioPosturalUseCase {
    DesvioPostural editar(EditarDesvioPosturalCommand command);
}
