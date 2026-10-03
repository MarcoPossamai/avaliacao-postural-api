package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DesvioPosturalJpaRepository extends JpaRepository<DesvioPosturalJpaEntity, Long>{
    List<DesvioPosturalJpaEntity> findByAvaliacaoIdOrderByIdAsc(Long avaliacaoId);
}
