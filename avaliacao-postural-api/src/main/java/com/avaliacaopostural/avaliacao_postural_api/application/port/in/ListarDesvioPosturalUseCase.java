package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;

public interface ListarDesvioPosturalUseCase {
    List<DesvioPostural> listarPorAvaliacao(Long avaliacaoId);
}
