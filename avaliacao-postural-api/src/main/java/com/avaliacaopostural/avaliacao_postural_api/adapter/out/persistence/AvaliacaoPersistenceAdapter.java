package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class AvaliacaoPersistenceAdapter implements AvaliacaoRepository{
    
    private final AvaliacaoJpaRepository avaliacaoJpaRepository;

    @Override 
    public Avaliacao salvar(Avaliacao avaliacao){
        AvaliacaoJpaEntity entity = toJpaEntity(avaliacao);
        AvaliacaoJpaEntity salvo = avaliacaoJpaRepository.save(entity);
        return toDomain(salvo);
    }

    @Override 
    public Optional<Avaliacao> buscarPorId(Long id){
        return avaliacaoJpaRepository.findById(id).map(this::toDomain);
    }

    @Override 
    public List<Avaliacao> listarPorAluno(Long alunoId){
        return avaliacaoJpaRepository.findByAlunoIdOrderByDataAvaliacaoDesc(alunoId)
            .stream().map(this::toDomain).toList();
    }

    private AvaliacaoJpaEntity toJpaEntity(Avaliacao avaliacao){
        return AvaliacaoJpaEntity.builder()
            .id(avaliacao.getId())
            .alunoId(avaliacao.getAlunoId())
            .dataAvaliacao(avaliacao.getDataAvaliacao())
            .observacoes(avaliacao.getObeservacoes())
            .build();
    }

    private Avaliacao toDomain(AvaliacaoJpaEntity entity){
        return new Avaliacao(entity.getId(), entity.getAlunoId(), entity.getDataAvaliacao(), entity.getObservacoes());
    }
}
