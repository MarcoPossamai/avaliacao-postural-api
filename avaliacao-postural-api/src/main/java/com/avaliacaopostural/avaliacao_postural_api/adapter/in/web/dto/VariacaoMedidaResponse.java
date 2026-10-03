package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.VariacaoMedida;

public record VariacaoMedidaResponse(
    String descricao,
    Double valorInicial,
    Double valorFinal,
    Double diferenca,
    Double percentual,
    String evolucao
) {
    public static VariacaoMedidaResponse from(VariacaoMedida v){
        return new VariacaoMedidaResponse(v.getDescricao(), v.getValorInicial(), v.getValorFinal(), 
            v.getDiferenca(), v.getPercentual(), v.getEvolucao());
    }
}
