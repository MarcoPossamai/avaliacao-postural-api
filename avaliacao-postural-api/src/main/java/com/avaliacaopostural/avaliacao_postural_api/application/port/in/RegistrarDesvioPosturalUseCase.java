package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;

public interface RegistrarDesvioPosturalUseCase {
    List<DesvioPostural> registrar(RegistrarDesvioPosturalCommand command);
}
