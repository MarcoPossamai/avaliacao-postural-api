package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.CadastrarExercicioRequest;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.ExercicioResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.GrupoMuscularResponse;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarExercicioUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarExerciciosUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarGruposMuscularesUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
public class ExercicioController {
    
    private final CadastrarExercicioUseCase cadastrarExercicioUseCase;
    private final ListarExerciciosUseCase listarExerciciosUseCase;
    private final ListarGruposMuscularesUseCase listarGruposMuscularesUseCase;

    @PostMapping ("/api/exercicios")
    public ResponseEntity<ExercicioResponse> cadastrar(@Valid @RequestBody CadastrarExercicioRequest request){
        var exercicio = cadastrarExercicioUseCase.cadastrar(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(ExercicioResponse.from(exercicio));
    }

    @GetMapping ("/api/exercicios")
    public List<ExercicioResponse> listar(){
        return listarExerciciosUseCase.listar().stream().map(ExercicioResponse::from).toList();
    }

    @GetMapping ("/api/grupos-musculares")
    public List<GrupoMuscularResponse> listarGrupos(){
        return listarGruposMuscularesUseCase.listar().stream().map(GrupoMuscularResponse::from).toList();
    }
}
