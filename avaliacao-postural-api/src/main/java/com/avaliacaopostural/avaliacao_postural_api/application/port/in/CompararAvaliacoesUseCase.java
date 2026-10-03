package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

public interface CompararAvaliacoesUseCase {
    ComparativoAvaliacoes comparar(Long avaliacaoInicialId, Long avaliacaoFinalId);
}
