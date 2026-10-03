package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedidaCorporalJpaRepository extends JpaRepository<MedidaCorporalJpaEntity, Long>{
    Optional<MedidaCorporalJpaEntity> findByAvaliacaoId(Long avaliacaoId);
}
