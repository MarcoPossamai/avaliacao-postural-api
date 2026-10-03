package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.DesvioPosturalResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.EditarDesvioPosturalRequest;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.RegistrarDesvioPosturalRequest;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarDesvioPosturalCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarDesvioPosturalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarDesvioPosturalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarDesvioPosturalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RemoverDesvioPosturalUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/avaliacoes/{avaliacaoId}/desvios")
@RequiredArgsConstructor 
public class DesvioPosturalController {
    
    private final RegistrarDesvioPosturalUseCase registrarDesvioPosturalUseCase;
    private final ListarDesvioPosturalUseCase listarDesvioPosturalUseCase;
    private final EditarDesvioPosturalUseCase editarDesvioPosturalUseCase;
    private final RemoverDesvioPosturalUseCase removerDesvioPosturalUseCase;

    @PostMapping 
    public ResponseEntity<List<DesvioPosturalResponse>> registrar(@PathVariable Long avaliacaoId, @Valid @RequestBody RegistrarDesvioPosturalRequest request){
        var salvos = registrarDesvioPosturalUseCase.registrar(request.toCommand(avaliacaoId));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(salvos.stream().map(DesvioPosturalResponse::from).toList());
    }

    @GetMapping 
    public List<DesvioPosturalResponse> listar(@PathVariable Long avaliacaoId){
        return listarDesvioPosturalUseCase.listarPorAvaliacao(avaliacaoId)
            .stream().map(DesvioPosturalResponse::from).toList();
    }

    @PutMapping ("/{desvioId}")
    public DesvioPosturalResponse editar(@PathVariable Long avaliacaoId, @PathVariable Long desvioId, @Valid @RequestBody EditarDesvioPosturalRequest request){
        var atualizado = editarDesvioPosturalUseCase.editar(new EditarDesvioPosturalCommand(avaliacaoId, desvioId, request.regiao(), request.tipo(), request.severidade()));
        return DesvioPosturalResponse.from(atualizado);
    }

    @DeleteMapping ("/{desvioId}")
    public ResponseEntity<Void> remover(@PathVariable Long avaliacaoId, @PathVariable Long desvioId){
        removerDesvioPosturalUseCase.remover(avaliacaoId, desvioId);
        return ResponseEntity.noContent().build();
    }
}
