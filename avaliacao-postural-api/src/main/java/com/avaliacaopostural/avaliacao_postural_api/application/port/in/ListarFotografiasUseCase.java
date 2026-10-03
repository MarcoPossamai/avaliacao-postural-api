package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;

public interface ListarFotografiasUseCase {
    List<Fotografia> listarPorAvaliacao(Long avaliacaoId);
}
