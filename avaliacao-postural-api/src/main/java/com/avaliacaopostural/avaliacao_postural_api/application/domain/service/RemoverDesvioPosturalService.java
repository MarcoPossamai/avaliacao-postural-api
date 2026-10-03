package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.DesvioNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RemoverDesvioPosturalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.DesvioPosturalRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RemoverDesvioPosturalService implements RemoverDesvioPosturalUseCase{
    
    private final DesvioPosturalRepository desvioPosturalRepository;

    @Override 
    @Transactional 
    public void remover(Long avaliacaoId, Long desvioId){
        desvioPosturalRepository.buscarPorId(desvioId)
            .filter(d -> d.getAvaliacaoId().equals(avaliacaoId))
            .orElseThrow(() -> new DesvioNaoEncontradoException(desvioId));
        desvioPosturalRepository.remover(desvioId);
    }
}
