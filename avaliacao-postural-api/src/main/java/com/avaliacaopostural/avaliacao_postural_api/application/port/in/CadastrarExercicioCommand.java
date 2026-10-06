package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

public record CadastrarExercicioCommand(
    String nome,
    String descricao,
    String urlVideo,
    Long grupoMuscularId
) {
    
}
