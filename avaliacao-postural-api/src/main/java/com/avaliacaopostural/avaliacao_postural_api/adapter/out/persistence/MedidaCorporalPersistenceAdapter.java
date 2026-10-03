package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Circunferencia;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.MedidaCorporalRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class MedidaCorporalPersistenceAdapter implements MedidaCorporalRepository{
    
    private final MedidaCorporalJpaRepository medidaCorporalJpaRepository;

    @Override 
    public MedidaCorporal salvar(MedidaCorporal medida){
        MedidaCorporalJpaEntity salvo = medidaCorporalJpaRepository.save(toJpaEntity(medida));
        return toDomain(salvo);
    }

    @Override 
    public Optional<MedidaCorporal> buscarPorAvaliacao(Long avaliacaoId){
        return medidaCorporalJpaRepository.findByAvaliacaoId(avaliacaoId).map(this::toDomain);
    }

    private MedidaCorporalJpaEntity toJpaEntity(MedidaCorporal medida){
        List<CircunferenciaJpaEmbeddable> circunferencias = medida.getCircunferencias().stream()
            .map(c -> new CircunferenciaJpaEmbeddable(c.getTipo(), c.getValorCm())).toList();

        return MedidaCorporalJpaEntity.builder()
            .id(medida.getId())
            .avaliacaoId(medida.getAvaliacaoId())
            .peso(medida.getPeso())
            .altura(medida.getAltura())
            .imc(medida.getImc())
            .percentualGordura(medida.getPercentualGordura())
            .circunferencias(new ArrayList<>(circunferencias))
            .build();
    }

    private MedidaCorporal toDomain(MedidaCorporalJpaEntity entity){
        List<Circunferencia> circunferencias = entity.getCircunferencias().stream()
            .map(c -> new Circunferencia(c.getTipo(), c.getValorCm()))
            .toList();

        return new MedidaCorporal(entity.getId(), entity.getAvaliacaoId(), entity.getPeso(), 
            entity.getAltura(), entity.getPercentualGordura(), circunferencias);
    }
}
