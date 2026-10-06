package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Entity 
@Table (name = "execucoes_treino")
public class ExecucaoTreinoJpaEntity {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long alunoId;
    private Long itemFichaId;
    private int numeroSerie;
    private Double cargaUtilizada;
    private Integer repeticoes;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFim;
    private String observacao;
}
