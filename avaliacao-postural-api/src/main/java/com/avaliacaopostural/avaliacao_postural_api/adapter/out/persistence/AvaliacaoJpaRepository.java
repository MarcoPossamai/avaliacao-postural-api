package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoJpaRepository extends JpaRepository<AvaliacaoJpaEntity, Long>{
    
    List<AvaliacaoJpaEntity> findByAlunoIdOrderByDataAvaliacaoDesc(Long alunoId);
}
