package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;

public interface RegistrarMedidaCorporalUseCase {
    
    MedidaCorporal registrar(RegistrarMedidaCorporalCommand command);
}
