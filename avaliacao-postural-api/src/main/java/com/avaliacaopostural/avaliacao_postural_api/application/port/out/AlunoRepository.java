package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.Optional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;

public interface AlunoRepository {
    
    Aluno salvar(Aluno aluno);
    
    Optional<Aluno> buscarPorId(Long id);

    Optional<Aluno> buscarPorEmail(String email);

    boolean existePorEmail(String email);

    void remover(Long id);
}
