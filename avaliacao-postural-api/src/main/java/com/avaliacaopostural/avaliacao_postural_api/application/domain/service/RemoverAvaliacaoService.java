package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RemoverAvaliacaoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ArmazenamentoArquivoPort;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FotografiaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemoverAvaliacaoService implements RemoverAvaliacaoUseCase{

    private final AvaliacaoRepository avaliacaoRepository;
    private final FotografiaRepository fotografiaRepository;
    private final ArmazenamentoArquivoPort armazenamentoArquivoPort;

    @Override
    @Transactional
    public void remover(Long avaliacaoId){
        avaliacaoRepository.buscarPorId(avaliacaoId)
            .orElseThrow(() -> new AvaliacaoNaoEncontradaException(avaliacaoId));

        List<String> arquivos = fotografiaRepository.listarPorAvaliacao(avaliacaoId).stream()
            .map(Fotografia::getCaminhoArquivo)
            .toList();

        avaliacaoRepository.remover(avaliacaoId);
        arquivos.forEach(armazenamentoArquivoPort::remover);
    }
}
