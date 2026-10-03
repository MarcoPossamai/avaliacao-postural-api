package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;

public record EditarAlunoCommand(
    Long alunoId,
    String nome,
    String email,
    String telefone,
    String sexo,
    LocalDate dataNascimento,
    String objetivos,
    String observacoes
) {
    
}
