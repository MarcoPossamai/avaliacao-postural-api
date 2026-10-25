package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.AvaliacaoResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.ComparativoAvaliacoesResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.DetalheAvaliacaoResponse;
import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.EditarAvaliacaoRequest;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CompararAvaliacoesUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ComparativoAvaliacoes;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.DetalheAvaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarAvaliacaoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ObterDetalheAvaliacaoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RemoverAvaliacaoUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/avaliacoes")
@RequiredArgsConstructor 
public class AvaliacaoDetalheController {
    
    private final ObterDetalheAvaliacaoUseCase obterDetalheAvaliacaoUseCase;
    private final CompararAvaliacoesUseCase compararAvaliacoesUseCase;
    private final EditarAvaliacaoUseCase editarAvaliacaoUseCase;
    private final RemoverAvaliacaoUseCase removerAvaliacaoUseCase;

    @GetMapping ("/{avaliacaoId}")
    public DetalheAvaliacaoResponse detalhe(@PathVariable Long avaliacaoId, @AuthenticationPrincipal Jwt jwt){
        DetalheAvaliacao detalhe = obterDetalheAvaliacaoUseCase.obter(avaliacaoId);
        verificarAcesso(detalhe.avaliacao().getAlunoId(), avaliacaoId, jwt);
        return DetalheAvaliacaoResponse.from(detalhe);
    }

    @GetMapping ("/comparar")
    public ComparativoAvaliacoesResponse comparar(@RequestParam ("inicial") Long inicialId, @RequestParam ("final") Long finalId, @AuthenticationPrincipal Jwt jwt){
        ComparativoAvaliacoes comparativo = compararAvaliacoesUseCase.comparar(inicialId, finalId);
        verificarAcesso(comparativo.avaliacaoInicial().avaliacao().getAlunoId(), inicialId, jwt);
        return ComparativoAvaliacoesResponse.from(comparativo);
    }

    private void verificarAcesso(Long alunoDonoId, Long avaliacaoId, Jwt jwt){
        boolean ehAluno = "ALUNO".equals(jwt.getClaimAsString("perfil"));
        if (ehAluno && !alunoDonoId.equals(Long.valueOf(jwt.getSubject()))) {
            throw new AvaliacaoNaoEncontradaException(avaliacaoId);
        }
    }

    @PutMapping ("/{avaliacaoId}")
    public AvaliacaoResponse editar(@PathVariable Long avaliacaoId, @Valid @RequestBody EditarAvaliacaoRequest request){
        return AvaliacaoResponse.from(editarAvaliacaoUseCase.editar(request.toCommand(avaliacaoId)));
    }

    @DeleteMapping ("/{avaliacaoId}")
    public ResponseEntity<Void> remover(@PathVariable Long avaliacaoId){
        removerAvaliacaoUseCase.remover(avaliacaoId);
        return ResponseEntity.noContent().build();
    }

}
