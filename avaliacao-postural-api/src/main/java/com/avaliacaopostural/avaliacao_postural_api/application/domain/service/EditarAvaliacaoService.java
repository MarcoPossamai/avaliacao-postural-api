package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarAvaliacaoCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarAvaliacaoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditarAvaliacaoService implements EditarAvaliacaoUseCase{

    private final AvaliacaoRepository avaliacaoRepository;

    @Override
    @Transactional
    public Avaliacao editar(EditarAvaliacaoCommand command){
        Avaliacao atual = avaliacaoRepository.buscarPorId(command.avaliacaoId())
            .orElseThrow(() -> new AvaliacaoNaoEncontradaException(command.avaliacaoId()));

        Avaliacao atualizada = new Avaliacao(atual.getId(), atual.getAlunoId(), 
            command.dataAvaliacao(), command.observacoes());
        return avaliacaoRepository.salvar(atualizada);
    }
}
