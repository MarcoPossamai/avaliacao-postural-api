package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarAlunoCommand;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record EditarAlunoRequest(
    @NotBlank String nome,
    @NotBlank @Email String email,
    @NotBlank String telefone,
    @NotBlank String sexo,
    @NotNull @Past LocalDate dataNascimento,
    String objetivos,
    String observacoes
) {
    public EditarAlunoCommand toCommand(Long alunoId){
        return new EditarAlunoCommand(alunoId, nome, email, telefone, sexo, dataNascimento, objetivos, observacoes);
    }
}
