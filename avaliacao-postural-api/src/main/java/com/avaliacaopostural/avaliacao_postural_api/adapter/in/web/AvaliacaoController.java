package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.AvaliacaoResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.CriarAvaliacaoRequest;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CriarAvaliacaoCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CriarAvaliacaoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarAvaliacoesUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/alunos/{alunoId}/avaliacoes")
@RequiredArgsConstructor 
public class AvaliacaoController {
    
    private final CriarAvaliacaoUseCase criarAvaliacaoUseCase;
    private final ListarAvaliacoesUseCase listarAvaliacoesUseCase;

    @PostMapping 
    public ResponseEntity<AvaliacaoResponse> criar(@PathVariable Long alunoId, @Valid @RequestBody CriarAvaliacaoRequest request){
        var command = new CriarAvaliacaoCommand(alunoId, request.dataAvaliacao(), request.observacoes());
        var avaliacao = criarAvaliacaoUseCase.criar(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(AvaliacaoResponse.from(avaliacao));
    }

    @GetMapping 
    public List<AvaliacaoResponse> listar(@PathVariable Long alunoId){
        return listarAvaliacoesUseCase.listarPorAluno(alunoId)
            .stream().map(AvaliacaoResponse::from).toList();        
    }
}
