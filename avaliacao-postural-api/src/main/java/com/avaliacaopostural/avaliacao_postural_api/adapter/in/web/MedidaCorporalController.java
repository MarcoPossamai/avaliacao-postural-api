package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.MedidaCorporalResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.RegistrarMedidaCorporalRequest;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarMedidaCorporalUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/avaliacoes/{avaliacaoId}/medida")
@RequiredArgsConstructor 
public class MedidaCorporalController {
    
    private final RegistrarMedidaCorporalUseCase registrarMedidaCorporalUseCase;

    @PostMapping 
    public ResponseEntity<MedidaCorporalResponse> registrar(@PathVariable Long avaliacaoId, @Valid @RequestBody RegistrarMedidaCorporalRequest request){
        var medida = registrarMedidaCorporalUseCase.registrar(request.toCommand(avaliacaoId));
        return ResponseEntity.status(HttpStatus.CREATED).body(MedidaCorporalResponse.from(medida));
    }
}
