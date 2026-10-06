package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.FichaNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ItemFichaInvalidaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ExecucaoTreino;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarExecucaoCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarExecucaoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ExecucaoTreinoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FichaRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RegistrarExecucaoService implements RegistrarExecucaoUseCase{
    
    private final FichaRepository fichaRepository;
    private final ExecucaoTreinoRepository execucaoTreinoRepository;

    @Override 
    @Transactional 
    public ExecucaoTreino registrar(RegistrarExecucaoCommand command){
        Ficha ficha = fichaRepository.buscarPorId(command.fichaId())
            .filter(f -> f.getAlunoId().equals(command.alunoId()))
            .orElseThrow(() -> new FichaNaoEncontradaException(command.fichaId()));

        boolean itemDaFicha = ficha.getItens().stream()
            .anyMatch(i -> i.getId().equals(command.itemFichaId()));
        if (!itemDaFicha) {
            throw new ItemFichaInvalidaException("o item não pertence a ficha");
        }

        return execucaoTreinoRepository.salvar(new ExecucaoTreino(null, command.alunoId(), command.itemFichaId(), command.numeroSerie(), 
            command.cargaUtilizada(), command.repeticoes(), command.horaInicio(), command.horaFim(), command.observacao()));
    }
}
