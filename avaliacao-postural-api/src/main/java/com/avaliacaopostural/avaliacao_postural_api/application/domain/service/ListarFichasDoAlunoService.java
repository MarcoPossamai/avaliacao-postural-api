package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ListarFichasUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FichaRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ListarFichasDoAlunoService implements ListarFichasUseCase{
    
    private final FichaRepository fichaRepository;

    @Override 
    @Transactional (readOnly = true)
    public List<Ficha> listarPorAluno(Long alunoId){
        return fichaRepository.listarPorAluno(alunoId);
    }
}
