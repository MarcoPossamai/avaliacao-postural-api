package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaJpaRepository extends JpaRepository<FichaJpaEntity, Long>{
    List<FichaJpaEntity> findByAlunoIdOrderByDataInicioDesc(Long alunoId);
}
