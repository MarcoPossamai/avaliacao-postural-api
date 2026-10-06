package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;

public interface ListarFichasUseCase {
    List<Ficha> listarPorAluno(Long alunoId);
}
