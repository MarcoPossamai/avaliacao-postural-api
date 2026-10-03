package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;

public interface ListarAvaliacoesUseCase {
    
    List<Avaliacao> listarPorAluno(Long alunoId);
}
