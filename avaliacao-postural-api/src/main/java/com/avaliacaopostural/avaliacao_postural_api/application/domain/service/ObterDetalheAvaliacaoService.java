package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.DetalheAvaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ObterDetalheAvaliacaoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.DesvioPosturalRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FotografiaRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.MedidaCorporalRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ObterDetalheAvaliacaoService implements ObterDetalheAvaliacaoUseCase{
    
    private final AvaliacaoRepository avaliacaoRepository;
    private final MedidaCorporalRepository medidaCorporalRepository;
    private final FotografiaRepository fotografiaRepository;
    private final DesvioPosturalRepository desvioPosturalRepository;

    @Override 
    @Transactional (readOnly = true)
    public DetalheAvaliacao obter(Long avaliacaoId){
        Avaliacao avaliacao = avaliacaoRepository.buscarPorId(avaliacaoId)
            .orElseThrow(() -> new AvaliacaoNaoEncontradaException(avaliacaoId));
        
        var medida = medidaCorporalRepository.buscarPorAvaliacao(avaliacaoId).orElse(null);
        var fotografias = fotografiaRepository.listarPorAvaliacao(avaliacaoId);
        var desvios = desvioPosturalRepository.listarPorAvaliacao(avaliacaoId);

        return new DetalheAvaliacao(avaliacao, medida, fotografias, desvios);
    }
}
