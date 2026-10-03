package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.List;
import java.util.Optional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;

public interface AvaliacaoRepository {
    
    Avaliacao salvar(Avaliacao avaliacao);

    Optional<Avaliacao> buscarPorId(Long id);

    List<Avaliacao> listarPorAluno(Long alunoId);
}
