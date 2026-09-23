package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;

public interface CadastrarAlunoUseCase {
    
    Aluno cadastrar(CadastrarAlunoCommand command);
}
