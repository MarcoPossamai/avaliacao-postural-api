package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarInstrutorCommand;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record CadastrarInstrutorRequest(
    @NotBlank String nome,
    @NotBlank @Email String email,
    @NotBlank @Size (min=8) String senha,
    @NotBlank String telefone,
    @NotBlank String sexo,
    @NotNull @Past LocalDate dataNascimento,
    @NotBlank String cref
) {
    public CadastrarInstrutorCommand toCommand(){
        return new CadastrarInstrutorCommand(nome, email, senha, telefone, 
            sexo, dataNascimento, cref
        );
    }
}
