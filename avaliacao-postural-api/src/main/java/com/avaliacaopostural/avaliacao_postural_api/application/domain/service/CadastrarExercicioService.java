package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.GrupoMuscularNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Exercicio;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarExercicioCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarExercicioUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ExercicioRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.GrupoMuscularRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CadastrarExercicioService implements CadastrarExercicioUseCase{
    
    private final ExercicioRepository exercicioRepository;
    private final GrupoMuscularRepository grupoMuscularRepository;

    @Override 
    @Transactional 
    public Exercicio cadastrar(CadastrarExercicioCommand command){
        grupoMuscularRepository.buscarPorId(command.grupoMuscularId())
            .orElseThrow(() -> new GrupoMuscularNaoEncontradoException(command.grupoMuscularId()));

        return exercicioRepository.salvar(new Exercicio(null, command.nome(), command.descricao(), 
            command.urlVideo(), command.grupoMuscularId()));
    }
}
