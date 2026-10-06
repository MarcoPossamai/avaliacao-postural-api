package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.List;
import java.util.Optional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;

public interface FichaRepository {
    Ficha salvar(Ficha ficha);
    Optional<Ficha> buscarPorId(Long id);
    List<Ficha> listarPorAluno(Long alunoId);
}
