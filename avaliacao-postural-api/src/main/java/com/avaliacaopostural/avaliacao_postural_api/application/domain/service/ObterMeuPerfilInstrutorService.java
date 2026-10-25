package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.InstrutorNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Instrutor;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ObterMeuPerfilInstrutorUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.InstrutorRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ObterMeuPerfilInstrutorService implements ObterMeuPerfilInstrutorUseCase{
    
    private final InstrutorRepository instrutorRepository;

    @Override 
    @Transactional (readOnly = true)
    public Instrutor obter(Long instrutorId){
        return instrutorRepository.buscarPorId(instrutorId)
            .orElseThrow(() -> new InstrutorNaoEncontradoException(instrutorId));
    }
}
