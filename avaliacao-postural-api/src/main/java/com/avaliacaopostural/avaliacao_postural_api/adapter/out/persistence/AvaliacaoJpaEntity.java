package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
@Table (name = "avaliacoes")
public class AvaliacaoJpaEntity {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (name = "aluno_id", nullable = false)
    private Long alunoId;

    @Column (nullable = false)
    private LocalDate dataAvaliacao;

    private String observacoes;
}
