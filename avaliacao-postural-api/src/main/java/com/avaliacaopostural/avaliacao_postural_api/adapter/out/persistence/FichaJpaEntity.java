package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table (name = "fichas")
public class FichaJpaEntity {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long alunoId;
    private Long instrutorId;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn (name = "ficha_id", nullable = false)
    @Builder.Default
    private List<ItemFichaJpaEntity> itens = new ArrayList<>();
}
