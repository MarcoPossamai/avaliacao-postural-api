package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecucaoTreinoJpaRepository extends JpaRepository<ExecucaoTreinoJpaEntity, Long>{
    List<ExecucaoTreinoJpaEntity> findByAlunoIdOrderByHoraInicioDesc(Long alunoId);
}
