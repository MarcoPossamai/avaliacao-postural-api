package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;

public record DetalheAvaliacao(
    Avaliacao avaliacao,
    MedidaCorporal medidaCorporal,
    List<Fotografia> fotografias,
    List<DesvioPostural> desvios
) {}
