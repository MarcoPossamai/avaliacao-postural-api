package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.util.List;

import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ComparativoAvaliacoes;


public record ComparativoAvaliacoesResponse(
    DetalheAvaliacaoResponse avaliacaoInicial,
    DetalheAvaliacaoResponse avaliacaoFinal,
    List<VariacaoMedidaResponse> variacoesMedidas,
    List<VariacaoDesvioResponse> variacoesDesvios
) {
    public static ComparativoAvaliacoesResponse from(ComparativoAvaliacoes comparativo){
        return new ComparativoAvaliacoesResponse(
            DetalheAvaliacaoResponse.from(comparativo.avaliacaoInicial()), 
            DetalheAvaliacaoResponse.from(comparativo.avaliacaoFinal()), 
            comparativo.variacoesMedidas().stream().map(VariacaoMedidaResponse::from).toList(),
            comparativo.variacoesDesvios().stream().map(VariacaoDesvioResponse::from).toList()
        );
    }
}
