package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.EditarFichaRequest;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.ExecucaoTreinoResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.FichaResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.PrescreverFichaRequest;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.RegistrarExecucaoRequest;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarFichaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarExecucoesUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarFichasUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.PrescreverFichaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarExecucaoUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
public class TreinoController {
    
    private final PrescreverFichaUseCase prescreverFichaUseCase;
    private final ListarFichasUseCase listarFichasUseCase;
    private final RegistrarExecucaoUseCase registrarExecucaoUseCase;
    private final ListarExecucoesUseCase listarExecucoesUseCase;
    private final EditarFichaUseCase editarFichaUseCase;

    @PostMapping ("/api/alunos/{alunoId}/fichas")
    public ResponseEntity<FichaResponse> prescrever(@PathVariable Long alunoId, @AuthenticationPrincipal Jwt jwt, @Valid @RequestBody PrescreverFichaRequest request){
        var ficha = prescreverFichaUseCase.prescrever(request.toCommand(alunoId, Long.valueOf(jwt.getSubject())));
        return ResponseEntity.status(HttpStatus.CREATED).body(FichaResponse.from(ficha));
    }

    @GetMapping ("/api/alunos/{alunoId}/fichas")
    public List<FichaResponse> fichasDoAluno(@PathVariable Long alunoId){
        return listarFichasUseCase.listarPorAluno(alunoId).stream().map(FichaResponse::from).toList();
    }
    
    @GetMapping ("/api/alunos/me/fichas")
    public List<FichaResponse> minhasFichas(@AuthenticationPrincipal Jwt jwt){
        return listarFichasUseCase.listarPorAluno(Long.valueOf(jwt.getSubject())).stream().map(FichaResponse::from).toList();
    }

    @PostMapping ("/api/fichas/{fichaId}/execucoes")
    public ResponseEntity<ExecucaoTreinoResponse> registrarExecucao(@PathVariable Long fichaId, @AuthenticationPrincipal Jwt jwt, @Valid @RequestBody RegistrarExecucaoRequest request){
        var execucao = registrarExecucaoUseCase.registrar(request.toCommand(fichaId, Long.valueOf(jwt.getSubject())));
        return ResponseEntity.status(HttpStatus.CREATED).body(ExecucaoTreinoResponse.from(execucao));
    }

    @GetMapping ("/api/alunos/me/execucoes")
    public List<ExecucaoTreinoResponse> minhasExecucoes(@AuthenticationPrincipal Jwt jwt){
        return listarExecucoesUseCase.listarPorAluno(Long.valueOf(jwt.getSubject())).stream().map(ExecucaoTreinoResponse::from).toList();
    }

    @PutMapping ("/api/fichas/{fichaId}")
    public FichaResponse editar(@PathVariable Long fichaId, @Valid @RequestBody EditarFichaRequest request){
        var ficha = editarFichaUseCase.editar(request.toCommand(fichaId));
        return FichaResponse.from(ficha);
    }
}
