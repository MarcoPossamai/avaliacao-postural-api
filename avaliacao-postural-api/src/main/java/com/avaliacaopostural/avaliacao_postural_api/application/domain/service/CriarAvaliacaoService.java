package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AlunoNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CriarAvaliacaoCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CriarAvaliacaoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CriarAvaliacaoService implements CriarAvaliacaoUseCase{
    
    private final AvaliacaoRepository avaliacaoRepository;
    private final AlunoRepository alunoRepository;

    @Override 
    @Transactional 
    public Avaliacao criar(CriarAvaliacaoCommand command){
        alunoRepository.buscarPorId(command.alunoId())
            .orElseThrow(() -> new AlunoNaoEncontradoException(command.alunoId()));
        
        Avaliacao avaliacao = new Avaliacao(null, command.alunoId(), command.dataAvaliacao(), command.obervacoes());
        return avaliacaoRepository.salvar(avaliacao);
    }
}
