package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;

public interface ListarAlunosUseCase {
    List<Aluno> listaDeAlunos();
}
