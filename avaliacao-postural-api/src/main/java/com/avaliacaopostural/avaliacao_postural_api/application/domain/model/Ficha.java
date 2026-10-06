package com.avaliacaopostural.avaliacao_postural_api.application.domain.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

@Getter 
public class Ficha {
    private final Long id;
    private final Long alunoId;
    private final Long instrutorId;
    private final LocalDate dataInicio;
    private final LocalDate dataFim;
    private final List<ItemFicha> itens;

    public Ficha(Long id, Long alunoId, Long instrutorId, LocalDate dataInicio, LocalDate dataFim, List<ItemFicha> itens){
        this.id = id;
        this.alunoId = alunoId;
        this.instrutorId = instrutorId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.itens = itens;
    }
}
