package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.RegiaoCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Severidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table (name = "desvios_posturais")
public class DesvioPosturalJpaEntity {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "avaliacao_id", nullable = false) 
    private Long avaliacaoId;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private RegiaoCorporal regiao;

    @Column (nullable = false)
    private String tipo;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private Severidade severidade;
}
