package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import lombok.Getter;

@Getter 
public class Circunferencia {
    
    private final String tipo;
    private final Double valorCm;

    public Circunferencia(String tipo, Double valorCm){
        this.tipo = tipo;
        this.valorCm = valorCm;
    }
}
