package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ExecucaoTreino;

public interface ListarExecucoesUseCase {
    List<ExecucaoTreino> listarPorAluno(Long alunoId);
}
