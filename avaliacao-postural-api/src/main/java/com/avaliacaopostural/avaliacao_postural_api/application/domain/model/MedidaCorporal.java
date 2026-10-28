package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.util.List;

import lombok.Getter;

@Getter 
public class MedidaCorporal {
    
    private static final List<String> DOBRAS_PERCENTUAL_GORDURA = List.of("Tríceps", "Subescapular", "Suprailíaca", "Panturrilha");

    private final Long id;
    private final Long avaliacaoId;
    private final Double peso;
    private final Double altura;
    private final Double imc;
    private final Double percentualGordura;
    private final List<Circunferencia> circunferencias;
    private final List<DobrasCutaneas> dobrasCutaneas;

    public MedidaCorporal(Long id, Long avaliacaoId, Double peso, Double altura, Double percentualGordura, List<Circunferencia> circunferencias, List<DobrasCutaneas> dobrasCutaneas){
        this.id = id;
        this.avaliacaoId = avaliacaoId;
        this.peso = peso;
        this.altura = altura;
        this.imc = calcularImc(peso, altura);
        this.percentualGordura = percentualGordura;
        this.circunferencias = circunferencias;
        this.dobrasCutaneas = dobrasCutaneas;
    }

    private static Double calcularImc(Double peso, Double altura){
        if(peso == null || altura == null || altura == 0){
            return null;
        }
        return peso / (altura * altura);
    }

    public static Double calcularPercentualGordura(String sexo, Integer idade, Double peso, Double altura, List<DobrasCutaneas> dobras){
        if(sexo == null || idade == null || peso == null || altura == null || dobras == null){
            return null;
        }
        if(!"M".equals(sexo) && !"F".equals(sexo)){
            return null;
        }
        Double soma = somarDobras(dobras, DOBRAS_PERCENTUAL_GORDURA);
        if(soma == null){
            return null;
        }

        double densidade = "M".equals(sexo)
            ? calcularDensidadeMasculina(soma, idade)
            : calcularDensidadeFeminina(soma, idade, peso, altura * 100);

        double percentual = ((4.95 / densidade) - 4.5) * 100;
        return Math.round(percentual * 100) / 100.0;
    }

    private static double calcularDensidadeMasculina(double soma, int idade){
        return 1.10726863
            - 0.00081201 * soma
            + 0.00000212 * soma * soma
            - 0.00041761 * idade;
    }

    private static double calcularDensidadeFeminina(double soma, int idade, double peso, double alturaCm){
        return 1.029002361
            - 0.00067159 * soma
            + 0.00000242 * soma * soma
            - 0.00026073 * idade
            - 0.00056009 * peso
            + 0.00054649 * alturaCm;
    }

    private static Double somarDobras(List<DobrasCutaneas> dobras, List<String> tipos){
        double soma = 0;
        for(String tipo : tipos){
            Double valor = dobras.stream()
                .filter(d -> tipo.equals(d.getTipo()))
                .map(DobrasCutaneas::getValorMm)
                .findFirst()
                .orElse(null);
            if(valor == null){
                return null;
            }
            soma += valor;
        }
        return soma;
    }
}
