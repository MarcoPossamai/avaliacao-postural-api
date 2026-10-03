package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table (name = "medidas_corporais")
public class MedidaCorporalJpaEntity {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "avaliacao_id", nullable = false, unique = true)
    private Long avaliacaoId;

    private Double peso;
    private Double altura;
    private Double imc;

    @Column (name = "percentual_gordura")
    private Double percentualGordura;

    @ElementCollection 
    @CollectionTable (name = "medida_circunferencias", joinColumns = @JoinColumn (name = "medida_corporal_id"))
    @Builder.Default
    private List<CircunferenciaJpaEmbeddable> circunferencias = new ArrayList<>();
}
