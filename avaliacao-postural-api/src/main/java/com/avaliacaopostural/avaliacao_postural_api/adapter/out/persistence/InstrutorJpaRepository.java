package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrutorJpaRepository extends JpaRepository<InstrutorJpaEntity, Long>{
    
    Optional<InstrutorJpaEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
