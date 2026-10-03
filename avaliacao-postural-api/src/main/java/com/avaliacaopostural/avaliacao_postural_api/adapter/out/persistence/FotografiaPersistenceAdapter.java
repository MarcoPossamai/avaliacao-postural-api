package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FotografiaRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class FotografiaPersistenceAdapter implements FotografiaRepository{

    private final FotografiaJpaRepository fotografiaJpaRepository;

    @Override 
    public Fotografia salvar(Fotografia fotografia){
        FotografiaJpaEntity salvo = fotografiaJpaRepository.save(toJpaEntity(fotografia));
        return toDomain(salvo);
    }

    @Override 
    public Optional<Fotografia> buscarPorId(Long id){
        return fotografiaJpaRepository.findById(id).map(this::toDomain);
    }

    @Override 
    public List<Fotografia> listarPorAvaliacao(Long avaliacaoId){
        return fotografiaJpaRepository.findByAvaliacaoIdOrderByDataUploadAsc(avaliacaoId)
            .stream().map(this::toDomain).toList();
    }

    @Override 
    public long contarPorAvaliacao(Long avaliacaoId){
        return fotografiaJpaRepository.countByAvaliacaoId(avaliacaoId);
    }

    @Override 
    public void remover(Long id){
        fotografiaJpaRepository.deleteById(id);        
    }

    private FotografiaJpaEntity toJpaEntity(Fotografia fotografia){
            return FotografiaJpaEntity.builder()
                .id(fotografia.getId())
                .avaliacaoId(fotografia.getAvaliacaoId())
                .caminhoArquivo(fotografia.getCaminhoArquivo())
                .contentType(fotografia.getContentType())
                .dataUpload(fotografia.getDataUpload())
                .build();
    }

    private Fotografia toDomain(FotografiaJpaEntity entity){
        return new Fotografia(entity.getId(), entity.getAvaliacaoId(), entity.getCaminhoArquivo(), 
            entity.getContentType(), entity.getDataUpload());
    }
}
