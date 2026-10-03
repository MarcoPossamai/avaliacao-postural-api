package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarAvaliacoesUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ListarAvaliacaoService implements ListarAvaliacoesUseCase{
    
    private final AvaliacaoRepository avaliacaoRepository;

    @Override 
    public List<Avaliacao> listarPorAluno(Long alunoId){
        return avaliacaoRepository.listarPorAluno(alunoId);
    }
}
