package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.time.LocalDateTime;

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
@Table (name = "fotografias")
public class FotografiaJpaEntity {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "avaliacao_id", nullable = false)
    private Long avaliacaoId;

    @Column (name = "caminho_arquivo", nullable = false)
    private String caminhoArquivo;

    @Column (name = "content_type", nullable = false)
    private String contentType;

    @Column (name = "data_upload", nullable = false)
    private LocalDateTime dataUpload;
}
