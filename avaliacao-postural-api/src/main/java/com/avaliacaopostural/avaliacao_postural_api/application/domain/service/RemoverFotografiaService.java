package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.FotografiaNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RemoverFotografiaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ArmazenamentoArquivoPort;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FotografiaRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RemoverFotografiaService implements RemoverFotografiaUseCase{

    private final FotografiaRepository fotografiaRepository;
    private final ArmazenamentoArquivoPort armazenamentoArquivoPort;

    @Override 
    @Transactional 
    public void remover(Long avaliacaoId, Long fotografiaId){
        Fotografia fotografia = fotografiaRepository.buscarPorId(fotografiaId)
            .filter(f -> f.getAvaliacaoId().equals(avaliacaoId))
            .orElseThrow(() -> new FotografiaNaoEncontradaException(fotografiaId));

        armazenamentoArquivoPort.remover(fotografia.getCaminhoArquivo());
        fotografiaRepository.remover(fotografiaId);
    }
}
