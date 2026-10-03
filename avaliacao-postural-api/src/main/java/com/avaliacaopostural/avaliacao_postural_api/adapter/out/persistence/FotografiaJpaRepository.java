package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FotografiaJpaRepository extends JpaRepository<FotografiaJpaEntity, Long>{
    List<FotografiaJpaEntity> findByAvaliacaoIdOrderByDataUploadAsc(Long avaliacaoId);
    long countByAvaliacaoId(Long avaliacaoId);
}
