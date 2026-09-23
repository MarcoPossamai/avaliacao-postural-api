package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.CadastrarInstrutorRequest;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.InstrutorResponse;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarInstrutorUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/instrutores")
@RequiredArgsConstructor 
public class InstrutorController {
    
    private final CadastrarInstrutorUseCase cadastrarInstrutorUseCase;

    @PostMapping 
    public ResponseEntity<InstrutorResponse> cadastrar(@Valid @RequestBody CadastrarInstrutorRequest request){
        var instrutor = cadastrarInstrutorUseCase.cadastrar(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(InstrutorResponse.from(instrutor));
    }
}
