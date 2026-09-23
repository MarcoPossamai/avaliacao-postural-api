package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;

public record AlunoResponse(
    Long id, 
    String nome, 
    String email, 
    String telefone, 
    String sexo,
    LocalDate dataNascimento,
    String objetivos,
    String observacoes
) {
    public static AlunoResponse from(Aluno aluno){
        return new AlunoResponse(aluno.getId(), aluno.getNome(),aluno.getEmail(),
        aluno.getTelefone(),aluno.getSexo(),aluno.getDataNascimento(),
        aluno.getObjetivos(),aluno.getObservacoes());

    }
}
