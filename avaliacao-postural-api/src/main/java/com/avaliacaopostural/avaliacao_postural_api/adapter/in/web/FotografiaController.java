package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto.FotografiaResponse;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AdicionarFotografiaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.BuscarConteudoFotografiaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarFotografiasUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RemoverFotografiaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AdicionarFotografiaCommand;

import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping ("/api/avaliacoes/{avaliacaoId}/fotografias")
@RequiredArgsConstructor 
public class FotografiaController {
    
    private final AdicionarFotografiaUseCase adicionarFotografiaUseCase;
    private final ListarFotografiasUseCase listarFotografiasUseCase;
    private final RemoverFotografiaUseCase removerFotografiaUseCase;
    private final BuscarConteudoFotografiaUseCase buscarConteudoFotografiaUseCase;

    @PostMapping (consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotografiaResponse> adicionar(@PathVariable Long avaliacaoId, @RequestParam ("arquivo") MultipartFile arquivo) throws IOException{
        
        var command = new AdicionarFotografiaCommand(avaliacaoId, arquivo.getOriginalFilename(),
            arquivo.getContentType(), arquivo.getBytes());
        
        var fotografia = adicionarFotografiaUseCase.adicionar(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(FotografiaResponse.from(fotografia));
    }

    @GetMapping 
    public List<FotografiaResponse> listar(@PathVariable Long avaliacaoId){
        return listarFotografiasUseCase.listarPorAvaliacao(avaliacaoId)
            .stream().map(FotografiaResponse::from).toList();
    }

    @GetMapping ("/{fotografiaId}/arquivo")
    public ResponseEntity<byte[]> arquivo(@PathVariable Long avaliacaoId, @PathVariable Long fotografiaId){
        var conteudo = buscarConteudoFotografiaUseCase.buscarConteudo(avaliacaoId, fotografiaId);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(conteudo.contentType()))
            .body(conteudo.bytes());
    }

    @DeleteMapping ("/{fotografiaId}")
    public ResponseEntity<Void> remover(@PathVariable Long avaliacaoId, @PathVariable Long fotografiaId){
        removerFotografiaUseCase.remover(avaliacaoId, fotografiaId);
        return ResponseEntity.noContent().build();
    }
}
