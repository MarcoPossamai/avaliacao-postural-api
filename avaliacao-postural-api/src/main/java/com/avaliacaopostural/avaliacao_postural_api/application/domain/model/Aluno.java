package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.time.LocalDate;

import lombok.Getter;

@Getter 
public class Aluno extends Usuario{
    
    private String objetivos;
    private String observacoes;
    
    public Aluno(Long id, String nome, String email, String senha, String telefone, String sexo,
            LocalDate dataNascimento, String objetivos, String observacoes) {
        super(id, nome, email, senha, telefone, sexo, dataNascimento);
        this.objetivos = objetivos;
        this.observacoes = observacoes;
    }


}
