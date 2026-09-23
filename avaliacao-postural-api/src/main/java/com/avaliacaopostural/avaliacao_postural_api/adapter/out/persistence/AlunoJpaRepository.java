package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoJpaRepository extends JpaRepository<AlunoJpaEntity, Long>{
    
    Optional<AlunoJpaEntity>findByEmail(String email);

    boolean existsByEmail(String email);
}
