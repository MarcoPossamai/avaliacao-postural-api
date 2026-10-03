package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.ComparativoAvaliacoesResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.DetalheAvaliacaoResponse;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CompararAvaliacoesUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ObterDetalheAvaliacaoUseCase;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/avaliacoes")
@RequiredArgsConstructor 
public class AvaliacaoDetalheController {
    
    private final ObterDetalheAvaliacaoUseCase obterDetalheAvaliacaoUseCase;
    private final CompararAvaliacoesUseCase compararAvaliacoesUseCase;

    @GetMapping ("/{avaliacaoId}")
    public DetalheAvaliacaoResponse detalhe(@PathVariable Long avaliacaoId){
        return DetalheAvaliacaoResponse.from(obterDetalheAvaliacaoUseCase.obter(avaliacaoId));
    }

    @GetMapping ("/comparar")
    public ComparativoAvaliacoesResponse comparar(@RequestParam ("inicial") Long inicialId, @RequestParam ("final") Long finalId){
        return ComparativoAvaliacoesResponse.from(compararAvaliacoesUseCase.comparar(inicialId, finalId));
    }
}
