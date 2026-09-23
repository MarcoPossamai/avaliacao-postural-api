package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;

public record CadastrarInstrutorCommand(
    String nome,
    String email,
    String senha,
    String telefone,
    String sexo,
    LocalDate dataNascimento,
    String cref
) {}
