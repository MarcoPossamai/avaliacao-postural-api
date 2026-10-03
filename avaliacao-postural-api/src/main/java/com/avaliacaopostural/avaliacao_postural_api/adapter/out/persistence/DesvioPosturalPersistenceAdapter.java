package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.DesvioPosturalRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class DesvioPosturalPersistenceAdapter implements DesvioPosturalRepository{
    
    private final DesvioPosturalJpaRepository jpaRepository;

    @Override
    public List<DesvioPostural> salvarTodos(List<DesvioPostural> desvios) {
        var entities = desvios.stream().map(this::toJpaEntity).toList();
        return jpaRepository.saveAll(entities).stream().map(this::toDomain).toList();
    }

    @Override
    public List<DesvioPostural> listarPorAvaliacao(Long avaliacaoId) {
        return jpaRepository.findByAvaliacaoIdOrderByIdAsc(avaliacaoId)
         .stream().map(this::toDomain).toList();
    }

    @Override 
    public Optional<DesvioPostural> buscarPorId(Long id){
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override 
    public DesvioPostural salvar(DesvioPostural desvio){
        return toDomain(jpaRepository.save(toJpaEntity(desvio)));
    }

    @Override 
    public void remover(Long id){
        jpaRepository.deleteById(id);
    }

    private DesvioPosturalJpaEntity toJpaEntity(DesvioPostural d){
        return DesvioPosturalJpaEntity.builder()
            .id(d.getId())
            .avaliacaoId(d.getAvaliacaoId())
            .regiao(d.getRegiao())
            .tipo(d.getTipo())
            .severidade(d.getSeveridade())
            .build();
    }

    private DesvioPostural toDomain(DesvioPosturalJpaEntity e){
        return new DesvioPostural(e.getId(), e.getAvaliacaoId(), e.getRegiao(), e.getTipo(), e.getSeveridade());
    }

}
