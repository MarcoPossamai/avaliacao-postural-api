package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.GrupoMuscular;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarGruposMuscularesUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.GrupoMuscularRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ListarGruposMuscularesService implements ListarGruposMuscularesUseCase{
    
    private final GrupoMuscularRepository grupoMuscularRepository;

    @Override 
    public List<GrupoMuscular> listar(){
        return grupoMuscularRepository.listarTodos();
    }
}
