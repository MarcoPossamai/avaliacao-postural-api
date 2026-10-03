package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AlunoNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RemoverAlunoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ArmazenamentoArquivoPort;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FotografiaRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RemoverAlunoService implements RemoverAlunoUseCase{
    
    private final AlunoRepository alunoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final FotografiaRepository fotografiaRepository;
    private final ArmazenamentoArquivoPort armazenamentoArquivoPort;

    @Override 
    @Transactional 
    public void remover(Long alunoId){
        alunoRepository.buscarPorId(alunoId)
            .orElseThrow(() -> new AlunoNaoEncontradoException(alunoId));

        List<String> arquivos = avaliacaoRepository.listarPorAluno(alunoId).stream()
            .flatMap(a -> fotografiaRepository.listarPorAvaliacao(a.getId()).stream())
            .map(Fotografia::getCaminhoArquivo)
            .toList();

        alunoRepository.remover(alunoId);
        arquivos.forEach(armazenamentoArquivoPort::remover);
    }
}
