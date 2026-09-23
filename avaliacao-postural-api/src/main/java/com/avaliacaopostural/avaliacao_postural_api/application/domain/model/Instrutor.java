package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.time.LocalDate;

public class Instrutor extends Usuario{
    
    private String cref;

    public Instrutor(Long id, String nome, String email, String senha, String telefone, String sexo,
            LocalDate dataNascimento, String cref) {
        super(id, nome, email, senha, telefone, sexo, dataNascimento);
        this.cref = cref;
    }

    public String getCref() {
        return cref;
    }

    
}
