package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarDesvioPosturalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.DesvioPosturalRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ListarDesvioPosturalService implements ListarDesvioPosturalUseCase{
    
    private final DesvioPosturalRepository desvioPosturalRepository;

    @Override 
    public List<DesvioPostural> listarPorAvaliacao(Long avaliacaoId){
        return desvioPosturalRepository.listarPorAvaliacao(avaliacaoId);
    }
}
