package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.GrupoMuscular;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.GrupoMuscularRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class GrupoMuscularPersistenceAdapter implements GrupoMuscularRepository{
    
    private final GrupoMuscularJpaRepository jpaRepository;

    @Override 
    public Optional<GrupoMuscular> buscarPorId(Long id){
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override 
    public List<GrupoMuscular> listarTodos(){
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    private GrupoMuscular toDomain(GrupoMuscularJpaEntity e){
        return new GrupoMuscular(e.getId(), e.getNome(), e.getDescricao());
    }
}
