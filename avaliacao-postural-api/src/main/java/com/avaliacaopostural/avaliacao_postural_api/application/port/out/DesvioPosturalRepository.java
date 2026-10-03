package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.List;
import java.util.Optional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;

public interface DesvioPosturalRepository {
    List<DesvioPostural> salvarTodos(List<DesvioPostural> desvios);
    List<DesvioPostural> listarPorAvaliacao(Long avaliacaoId);
    Optional<DesvioPostural> buscarPorId(Long id);
    DesvioPostural salvar(DesvioPostural desvio);
    void remover(Long id);
}
