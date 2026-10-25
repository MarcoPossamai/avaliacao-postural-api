package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

@Getter 
public class Ficha {
    private final Long id;
    private final Long alunoId;
    private final Long instrutorId;
    private final String nome;
    private final LocalDate dataInicio;
    private final LocalDate dataFim;
    private final Integer frequenciaSemanal;
    private final Long avaliacaoReferenciaId;
    private final List<ItemFicha> itens;

    public Ficha(Long id, Long alunoId, Long instrutorId, String nome, LocalDate dataInicio, LocalDate dataFim, 
            Integer frequenciaSemanal, Long avaliacaoReferenciaId, List<ItemFicha> itens){
        this.id = id;
        this.alunoId = alunoId;
        this.instrutorId = instrutorId;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.frequenciaSemanal = frequenciaSemanal;
        this.avaliacaoReferenciaId = avaliacaoReferenciaId;
        this.itens = itens;
    }
}
