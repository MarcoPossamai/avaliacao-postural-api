package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDateTime;

public record RegistrarExecucaoCommand(
    Long fichaId,
    Long alunoId,
    Long itemFichaId,
    int numeroSerie,
    Double cargaUtilizada,
    Integer repeticoes,
    LocalDateTime horaInicio,
    LocalDateTime horaFim,
    String observacao
) {
    
}
