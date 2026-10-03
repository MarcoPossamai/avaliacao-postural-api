package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

public interface RemoverDesvioPosturalUseCase {
    void remover(Long avaliacaoId, Long desvioId);
}
