package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.AlunoResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.CadastrarAlunoRequest;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.EditarAlunoRequest;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarAlunoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarAlunoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarAlunosUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RemoverAlunoUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/alunos")
@RequiredArgsConstructor 
public class AlunoController {
    
    private final CadastrarAlunoUseCase cadastrarAlunoUseCase;
    private final EditarAlunoUseCase editarAlunoUseCase;
    private final ListarAlunosUseCase listarAlunosUseCase;
    private final RemoverAlunoUseCase removerAlunoUseCase;

    @PostMapping 
    public ResponseEntity<AlunoResponse> cadastrar(@Valid @RequestBody CadastrarAlunoRequest request){
        var aluno = cadastrarAlunoUseCase.cadastrar(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(AlunoResponse.from(aluno));
    }

    @PutMapping ("/me")
    public AlunoResponse editarMeuPerfil(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody EditarAlunoRequest request){
        Long id = Long.valueOf(jwt.getSubject());
        return AlunoResponse.from(editarAlunoUseCase.editar(request.toCommand(id)));
    }

    @GetMapping 
    public List<AlunoResponse> listar(){
        return listarAlunosUseCase.listaDeAlunos().stream().map(AlunoResponse::from).toList();
    }

    @DeleteMapping ("/{alunoId}")
    public ResponseEntity<Void> remover(@PathVariable Long alunoId){
        removerAlunoUseCase.remover(alunoId);
        return ResponseEntity.noContent().build();
    }
}
