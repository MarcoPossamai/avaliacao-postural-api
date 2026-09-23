package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter 
@Setter 
@NoArgsConstructor 
@SuperBuilder 
@MappedSuperclass 
public abstract class UsuarioJpaEntity{
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String nome;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String senha;

    private String telefone;

    private String sexo;

    private LocalDate dataNascimento;
}