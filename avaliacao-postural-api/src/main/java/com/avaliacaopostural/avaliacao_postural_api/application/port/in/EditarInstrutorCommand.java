package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;

public record EditarInstrutorCommand(
    Long instrutorId,
    String nome,
    String email,
    String telefone,
    String sexo,
    LocalDate dataNascimento,
    String cref
) {
    
}
