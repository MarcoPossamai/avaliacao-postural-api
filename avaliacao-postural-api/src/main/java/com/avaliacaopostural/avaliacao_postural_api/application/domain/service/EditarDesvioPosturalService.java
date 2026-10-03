package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.DesvioNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarDesvioPosturalCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarDesvioPosturalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.DesvioPosturalRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class EditarDesvioPosturalService implements EditarDesvioPosturalUseCase{
    
    private final DesvioPosturalRepository desvioPosturalRepository;

    @Override 
    @Transactional 
    public DesvioPostural editar(EditarDesvioPosturalCommand command){
        DesvioPostural existente = desvioPosturalRepository.buscarPorId(command.desvioId())
            .filter(d -> d.getAvaliacaoId().equals(command.avaliacaoId()))
            .orElseThrow(() -> new DesvioNaoEncontradoException(command.desvioId()));

        DesvioPostural atualizado = new DesvioPostural(existente.getId(), existente.getAvaliacaoId(), 
            existente.getRegiao(), existente.getTipo(), existente.getSeveridade());
        return desvioPosturalRepository.salvar(atualizado);
    }
}
