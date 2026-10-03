package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import lombok.Getter;

@Getter 
public class VariacaoMedida {
    private final String descricao;
    private final Double valorInicial;
    private final Double valorFinal;
    private final Double diferenca;
    private final Double percentual;
    private final String evolucao;

    public VariacaoMedida(String descricao, Double valorInicial, Double valorFinal){
        this.descricao = descricao;
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.diferenca = (valorInicial != null && valorFinal != null) ? valorFinal - valorInicial : null;
        this.percentual = (diferenca != null && valorInicial != null && valorInicial != 0) ? (diferenca / valorInicial) * 100 : null;
        this.evolucao = calcularEvolucao(diferenca);
    }

    private static String calcularEvolucao(Double diferenca){
        if (diferenca == null) return "sem dados";
        if (diferenca > 0) return "aumento";
        if (diferenca < 0) return "reducao";
        return "estavel"; 
    }
}
