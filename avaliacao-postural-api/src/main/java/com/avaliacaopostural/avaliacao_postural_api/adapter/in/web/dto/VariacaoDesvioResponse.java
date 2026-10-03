package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.SituacaoDesvio;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.VariacaoDesvio;

public record VariacaoDesvioResponse(
    RegiaoCorporal regiao,
    String tipo,
    Severidade severidadeInicial,
    Severidade severidadeFinal,
    SituacaoDesvio situacao
) {
    public static VariacaoDesvioResponse from(VariacaoDesvio v){
        return new VariacaoDesvioResponse(v.getRegiao(), v.getTipo(), v.getSeveridadeInicial(), v.getSeveridadeFinal(), v.getSituacao());
    }
}
