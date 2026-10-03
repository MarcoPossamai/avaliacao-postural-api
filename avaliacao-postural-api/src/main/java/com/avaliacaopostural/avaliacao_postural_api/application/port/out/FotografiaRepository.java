package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.List;
import java.util.Optional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;

public interface FotografiaRepository {
    Fotografia salvar(Fotografia fotografia);
    Optional<Fotografia> buscarPorId(Long id);
    List<Fotografia> listarPorAvaliacao(Long avaliacaoId);
    long contarPorAvaliacao(Long avaliacaoId);
    void remover(Long id);
}
