package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AlunoNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ObterMeuPerfilUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ObterMeuPerfilService implements ObterMeuPerfilUseCase{
    
    private final AlunoRepository alunoRepository;

    @Override 
    @Transactional (readOnly = true)
    public Aluno obter(Long alunoId){
        return alunoRepository.buscarPorId(alunoId)
            .orElseThrow(() -> new AlunoNaoEncontradoException(alunoId));
    }
}
