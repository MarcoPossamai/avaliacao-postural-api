package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ExecucaoTreino;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ExecucaoTreinoRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ExecucaoTreinoPersistenceAdapter implements ExecucaoTreinoRepository{
    
    private final ExecucaoTreinoJpaRepository jpaRepository;

    @Override 
    public ExecucaoTreino salvar(ExecucaoTreino execucao){
        ExecucaoTreinoJpaEntity salva = jpaRepository.save(ExecucaoTreinoJpaEntity.builder()
            .id(execucao.getId()).alunoId(execucao.getAlunoId()).itemFichaId(execucao.getItemFichaId())
            .numeroSerie(execucao.getNumeroSerie()).cargaUtilizada(execucao.getCargaUtilizada())
            .repeticoes(execucao.getRepeticoes()).horaInicio(execucao.getHoraInicio()).horaFim(execucao.getHoraFim())
            .observacao(execucao.getObservacao()).build());
        
        return toDomain(salva);
    }

    @Override 
    public List<ExecucaoTreino> listarPorAluno(Long alunoId){
        return jpaRepository.findByAlunoIdOrderByHoraInicioDesc(alunoId)
            .stream().map(this::toDomain).toList();
    }

    private ExecucaoTreino toDomain(ExecucaoTreinoJpaEntity entity){
        return new ExecucaoTreino(
            entity.getId(), 
            entity.getAlunoId(), 
            entity.getItemFichaId(), 
            entity.getNumeroSerie(), 
            entity.getCargaUtilizada(), 
            entity.getRepeticoes(), 
            entity.getHoraInicio(), 
            entity.getHoraFim(), 
            entity.getObservacao()
        );
    }
}
