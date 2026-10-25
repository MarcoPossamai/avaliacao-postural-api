package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;

public interface EditarMedidaCorporalUseCase {
    MedidaCorporal editar(RegistrarMedidaCorporalCommand command);
}
