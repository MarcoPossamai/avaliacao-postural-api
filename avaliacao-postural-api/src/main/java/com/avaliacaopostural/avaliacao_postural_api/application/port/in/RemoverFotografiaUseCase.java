package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

public interface RemoverFotografiaUseCase {
    void remover(Long avaliacaoId, Long fotografiaId);
}
