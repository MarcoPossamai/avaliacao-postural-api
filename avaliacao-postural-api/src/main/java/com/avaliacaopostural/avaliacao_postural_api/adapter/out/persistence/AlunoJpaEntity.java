package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter 
@Setter 
@NoArgsConstructor 
@SuperBuilder 
@Entity 
@Table (name = "alunos")
public class AlunoJpaEntity extends UsuarioJpaEntity{
    
    private String objetivos;

    private String observacoes;
}
