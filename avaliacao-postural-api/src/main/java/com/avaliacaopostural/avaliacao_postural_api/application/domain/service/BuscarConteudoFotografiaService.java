package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.FotografiaNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.BuscarConteudoFotografiaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ConteudoArquivo;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ArmazenamentoArquivoPort;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FotografiaRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class BuscarConteudoFotografiaService implements BuscarConteudoFotografiaUseCase{
    
    private final FotografiaRepository fotografiaRepository;
    private final ArmazenamentoArquivoPort armazenamentoArquivoPort;

    @Override 
    public ConteudoArquivo buscarConteudo(Long avaliacaoId, Long fotografiaId){
        Fotografia fotografia = fotografiaRepository.buscarPorId(fotografiaId)
            .filter(f -> f.getAvaliacaoId().equals(avaliacaoId))
            .orElseThrow(() -> new FotografiaNaoEncontradaException(fotografiaId));

        byte[] conteudo = armazenamentoArquivoPort.ler(fotografia.getCaminhoArquivo());
        return new ConteudoArquivo(conteudo, fotografia.getContentType());
    }
}
