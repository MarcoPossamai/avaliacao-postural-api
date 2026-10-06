package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Exercicio;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarExerciciosUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ExercicioRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ListarExerciciosService implements ListarExerciciosUseCase{
    
    private final ExercicioRepository exercicioRepository;

    @Override 
    public List<Exercicio> listar(){
        return exercicioRepository.listarTodos();
    }
}
