package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

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
@Table (name = "itens_ficha")
public class ItemFichaJpaEntity {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long exercicioId;
    private int ordem;
    private int series;
    private int repeticoesMin;
    private int repeticoesMax;
    private int descanso;
}
