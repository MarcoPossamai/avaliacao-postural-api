package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ItemFicha;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FichaRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class FichaPersistenceAdapter implements FichaRepository{
    
    private final FichaJpaRepository jpaRepository;

    @Override 
    public Ficha salvar(Ficha ficha){
        List<ItemFichaJpaEntity> itens = ficha.getItens().stream()
            .map(i -> ItemFichaJpaEntity.builder()
                .exercicioId(i.getExercicioId()).ordem(i.getOrdem()).series(i.getSeries()).repeticoesMin(i.getRepeticoesMin()).repeticoesMax(i.getRepeticoesMax()).descanso(i.getDescanso()).build())
            .collect(Collectors.toCollection(ArrayList::new));

        FichaJpaEntity salva = jpaRepository.save(FichaJpaEntity.builder()
            .id(ficha.getId()).alunoId(ficha.getAlunoId()).instrutorId(ficha.getInstrutorId()).dataInicio(ficha.getDataInicio()).dataFim(ficha.getDataFim()).itens(itens).build());
        return toDomain(salva);
    }

    @Override 
    public Optional<Ficha> buscarPorId(Long id){
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override 
    public List<Ficha> listarPorAluno(Long alunoId){
        return jpaRepository.findByAlunoIdOrderByDataInicioDesc(alunoId).stream().map(this::toDomain).toList();
    }

    private Ficha toDomain(FichaJpaEntity e){
        List<ItemFicha> itens = e.getItens().stream()
            .map(i -> new ItemFicha(i.getId(), i.getExercicioId(), i.getOrdem(), i.getSeries(), 
                i.getRepeticoesMin(), i.getRepeticoesMax(), i.getDescanso())).toList();

            return new Ficha(e.getId(), e.getAlunoId(), e.getInstrutorId(), e.getDataInicio(), e.getDataFim(), itens);
    }
}
