package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.List;
import java.util.Optional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Exercicio;

public interface ExercicioRepository {
    Exercicio salvar(Exercicio exercicio);
    Optional<Exercicio> buscarPorId(Long id);
    List<Exercicio> listarTodos();
}
