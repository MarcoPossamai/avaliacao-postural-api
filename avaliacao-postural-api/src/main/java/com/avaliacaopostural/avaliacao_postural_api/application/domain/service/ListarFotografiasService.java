package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarFotografiasUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FotografiaRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ListarFotografiasService implements ListarFotografiasUseCase{
    
    private final FotografiaRepository fotografiaRepository;

    @Override 
    public List<Fotografia> listarPorAvaliacao(Long avaliacaoId){
        return fotografiaRepository.listarPorAvaliacao(avaliacaoId);
    }
}
