package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.LoginRequest;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.LoginResponse;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AutenticarUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/auth")
@RequiredArgsConstructor 
public class AuthController {
    
    private final AutenticarUseCase autenticarUseCase;

    @PostMapping ("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request){
        return LoginResponse.from(autenticarUseCase.autenticar(request.toCommand()));
    }
}
