package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.List;
import java.util.Optional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.GrupoMuscular;

public interface GrupoMuscularRepository {
    List<GrupoMuscular> listarTodos();
    Optional<GrupoMuscular> buscarPorId(Long id);
}
