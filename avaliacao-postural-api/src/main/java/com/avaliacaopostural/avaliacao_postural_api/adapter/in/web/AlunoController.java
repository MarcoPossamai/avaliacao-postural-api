package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.AlunoResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.CadastrarAlunoRequest;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarAlunoUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/alunos")
@RequiredArgsConstructor 
public class AlunoController {
    
    private final CadastrarAlunoUseCase cadastrarAlunoUseCase;

    @PostMapping 
    public ResponseEntity<AlunoResponse> cadastrar(@Valid @RequestBody CadastrarAlunoRequest request){
        var aluno = cadastrarAlunoUseCase.cadastrar(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(AlunoResponse.from(aluno));
    }
}
