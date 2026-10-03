package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarDesvioPosturalCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarDesvioPosturalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.DesvioPosturalRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RegistrarDesvioPosturalService implements RegistrarDesvioPosturalUseCase{
    
    private final DesvioPosturalRepository desvioPosturalRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    @Override 
    @Transactional 
    public List<DesvioPostural> registrar(RegistrarDesvioPosturalCommand command){
        avaliacaoRepository.buscarPorId(command.avaliacaoId())
            .orElseThrow(() -> new AvaliacaoNaoEncontradaException(command.avaliacaoId()));
        
        List<DesvioPostural> desvios = command.desvios().stream()
            .map(d -> new DesvioPostural(null, command.avaliacaoId(), d.regiao(), d.tipo(), d.severidade()))
            .toList();
        
        return desvioPosturalRepository.salvarTodos(desvios);
    }
}
