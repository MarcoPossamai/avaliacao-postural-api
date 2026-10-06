package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Exercicio;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ExercicioRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ExercicioPersistenceAdapter implements ExercicioRepository{
    
    private final ExercicioJpaRepository jpaRepository;

    @Override 
    public Exercicio salvar(Exercicio exercicio){
        return toDomain(jpaRepository.save(toJpaEntity(exercicio)));
    }

    @Override 
    public Optional<Exercicio> buscarPorId(Long id){
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override 
    public List<Exercicio> listarTodos(){
        return jpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    private ExercicioJpaEntity toJpaEntity(Exercicio e){
        return ExercicioJpaEntity.builder()
            .id(e.getId())
            .nome(e.getNome())
            .descricao(e.getDescricao())
            .urlVideo(e.getUrlVideo())
            .grupoMuscularId(e.getGrupoMuscularId())
            .build();
    }

    private Exercicio toDomain(ExercicioJpaEntity e){
        return new Exercicio(e.getId(), e.getNome(), e.getDescricao(), 
            e.getUrlVideo(), e.getGrupoMuscularId());
    }
}
