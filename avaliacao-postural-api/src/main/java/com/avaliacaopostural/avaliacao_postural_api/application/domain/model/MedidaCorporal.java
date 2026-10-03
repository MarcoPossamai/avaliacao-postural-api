package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.util.List;

import lombok.Getter;

@Getter 
public class MedidaCorporal {
    
    private final Long id;
    private final Long avaliacaoId;
    private final Double peso;
    private final Double altura;
    private final Double imc;
    private final Double percentualGordura;
    private final List<Circunferencia> circunferencias;

    public MedidaCorporal(Long id, Long avaliacaoId, Double peso, Double altura, Double percentualGordura, List<Circunferencia> circunferencias){
        this.id = id;
        this.avaliacaoId = avaliacaoId;
        this.peso = peso;
        this.altura = altura;
        this.imc = calcularImc(peso, altura);
        this.percentualGordura = percentualGordura;
        this.circunferencias = circunferencias;
    }

    private static Double calcularImc(Double peso, Double altura){
        if(peso == null || altura == null || altura == 0){
            return null;
        }
        return peso / (altura * altura);
    }
}
