package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.CadastrarInstrutorRequest;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.EditarInstrutorRequest;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.InstrutorResponse;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarInstrutorUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarInstrutorUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/instrutores")
@RequiredArgsConstructor 
public class InstrutorController {
    
    private final CadastrarInstrutorUseCase cadastrarInstrutorUseCase;
    private final EditarInstrutorUseCase editarInstrutorUseCase;

    @PostMapping 
    public ResponseEntity<InstrutorResponse> cadastrar(@Valid @RequestBody CadastrarInstrutorRequest request){
        var instrutor = cadastrarInstrutorUseCase.cadastrar(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(InstrutorResponse.from(instrutor));
    }

    @PutMapping ("/me")
    public InstrutorResponse editarMeuPerfil(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody EditarInstrutorRequest request){
        Long id = Long.valueOf(jwt.getSubject());
        return InstrutorResponse.from(editarInstrutorUseCase.editar(request.toCommand(id)));
    }
}
