package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarAlunosUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ListarAlunosService implements ListarAlunosUseCase{
    
    private final AlunoRepository alunoRepository;

    @Override 
    public List<Aluno> listaDeAlunos(){
        return alunoRepository.listarTodos();
    }
}
