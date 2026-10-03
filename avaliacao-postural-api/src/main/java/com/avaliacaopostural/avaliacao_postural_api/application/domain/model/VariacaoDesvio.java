package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import lombok.Getter;

@Getter 
public class VariacaoDesvio {
    private final RegiaoCorporal regiao;
    private final String tipo;
    private final Severidade severidadeInicial;
    private final Severidade severidadeFinal;
    private final SituacaoDesvio situacao;

    public VariacaoDesvio(RegiaoCorporal regiao, String tipo, Severidade inicial, Severidade fim){
        this.regiao = regiao;
        this.tipo = tipo;
        this.severidadeInicial = inicial;
        this.severidadeFinal = fim;
        this.situacao = calcularSituacao(inicial, fim);
    }

    private static SituacaoDesvio calcularSituacao(Severidade inicial, Severidade fim){
        if(inicial == null) return SituacaoDesvio.APARECEU;
        if(fim == null) return SituacaoDesvio.DESAPARECEU;
        int diferenca = fim.ordinal() - inicial.ordinal();
        if(diferenca > 0) return SituacaoDesvio.AGRAVOU;
        if(diferenca < 0) return SituacaoDesvio.MELHOROU;
        return SituacaoDesvio.MANTIDO;
    }
}
