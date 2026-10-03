package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.VariacaoDesvio;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.VariacaoMedida;

public record ComparativoAvaliacoes(
    DetalheAvaliacao avaliacaoInicial,
    DetalheAvaliacao avaliacaoFinal,
    List<VariacaoMedida> variacoesMedidas,
    List<VariacaoDesvio> variacoesDesvios
) {}
