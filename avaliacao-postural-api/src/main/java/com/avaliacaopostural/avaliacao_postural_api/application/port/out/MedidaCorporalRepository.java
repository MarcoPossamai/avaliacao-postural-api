package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.Optional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;

public interface MedidaCorporalRepository {

    MedidaCorporal salvar(MedidaCorporal medida);
    Optional<MedidaCorporal> buscarPorAvaliacao(Long avaliacaoId);
}
