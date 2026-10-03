package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarInstrutorCommand;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record EditarInstrutorRequest(
    @NotBlank String nome,
    @NotBlank @Email String email,
    @NotBlank String telefone,
    @NotBlank String sexo,
    @NotNull @Past LocalDate dataNascimento,
    @NotBlank String cref
) {
    public EditarInstrutorCommand toCommand(Long instrutorId){
        return new EditarInstrutorCommand(instrutorId, nome, email, telefone, sexo, dataNascimento, cref);
    }
}
