package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ExecucaoTreino;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarExecucoesUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ExecucaoTreinoRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ListarExecucoesDoAlunoService implements ListarExecucoesUseCase{
    
    private final ExecucaoTreinoRepository execucaoTreinoRepository;

    @Override 
    public List<ExecucaoTreino> listarPorAluno(Long alunoId){
        return execucaoTreinoRepository.listarPorAluno(alunoId);
    }
}
