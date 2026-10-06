package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ExecucaoTreino;

public interface ExecucaoTreinoRepository {
    ExecucaoTreino salvar(ExecucaoTreino execucao);
    List<ExecucaoTreino> listarPorAluno(Long alunoId);
}
