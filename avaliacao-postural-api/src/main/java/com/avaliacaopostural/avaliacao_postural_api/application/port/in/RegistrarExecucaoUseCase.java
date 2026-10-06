package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ExecucaoTreino;

public interface RegistrarExecucaoUseCase {
    ExecucaoTreino registrar(RegistrarExecucaoCommand command);
}
