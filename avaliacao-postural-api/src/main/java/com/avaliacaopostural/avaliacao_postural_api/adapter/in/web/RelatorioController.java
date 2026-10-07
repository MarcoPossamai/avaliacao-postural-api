package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import java.time.LocalDate;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.RelatorioEvolucaoResponse;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.GerarRelatorioEvolucaoUseCase;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
public class RelatorioController {
    
    private final GerarRelatorioEvolucaoUseCase gerarRelatorioEvolucaoUseCase;

    @GetMapping ("/api/alunos/{alunoId}/relatorio-evolucao")
    public RelatorioEvolucaoResponse relatorioDoAluno(@PathVariable Long alunoId, @RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        return RelatorioEvolucaoResponse.from(gerarRelatorioEvolucaoUseCase.gerar(alunoId, inicio, fim));
    }

    @GetMapping ("/api/alunos/me/relatorio-evolucao")
    public RelatorioEvolucaoResponse meuRelatorio(@AuthenticationPrincipal Jwt jwt, @RequestParam LocalDate inicio, @RequestParam LocalDate fim){
        return RelatorioEvolucaoResponse.from(gerarRelatorioEvolucaoUseCase.gerar(Long.valueOf(jwt.getSubject()), inicio, fim));
    }
}
