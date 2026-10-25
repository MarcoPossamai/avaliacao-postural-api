package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import lombok.Getter;

@Getter 
public class DobrasCutaneas {

    private final String tipo;
    private final Double valorMm;

    public DobrasCutaneas(String tipo, Double valorMm){
        this.tipo = tipo;
        this.valorMm = valorMm;
    }
}
