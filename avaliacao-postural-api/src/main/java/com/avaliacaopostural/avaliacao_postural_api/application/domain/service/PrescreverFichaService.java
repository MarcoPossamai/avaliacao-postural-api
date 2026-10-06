package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AlunoNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ExercicioNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ItemFicha;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.PrescreverFichaCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.PrescreverFichaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ExercicioRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FichaRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class PrescreverFichaService implements PrescreverFichaUseCase{
    
    private final FichaRepository fichaRepository;
    private final ExercicioRepository exercicioRepository;
    private final AlunoRepository alunoRepository;

    @Override 
    @Transactional 
    public Ficha prescrever(PrescreverFichaCommand command){
        alunoRepository.buscarPorId(command.alunoId())
            .orElseThrow(() -> new AlunoNaoEncontradoException(command.alunoId()));

        List<ItemFicha> itens = command.itens().stream().map(i -> {
            exercicioRepository.buscarPorId(i.exercicioId())
                .orElseThrow(() -> new ExercicioNaoEncontradoException(i.exercicioId()));
            return new ItemFicha(null, i.exercicioId(), i.ordem(), i.series(), i.repeticoesMin(), i.repeticoesMax(), i.descanso());
        }).toList();

        return fichaRepository.salvar(new Ficha(null, command.alunoId(), command.instrutorId(), 
            command.dataInicio(), command.dataFim(), itens));
    }
}
