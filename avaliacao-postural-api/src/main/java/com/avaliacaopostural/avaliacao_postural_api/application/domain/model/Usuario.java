package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.time.LocalDate;

import lombok.Getter;

@Getter 
public abstract class Usuario {

    protected Long id;
    protected String nome;
    protected String email;
    protected String senha;
    protected String telefone;
    protected String sexo;
    protected LocalDate dataNascimento;

    protected Usuario(Long id, String nome, String email, String senha,
        String telefone, String sexo, LocalDate dataNascimento
    ){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
    }

}
