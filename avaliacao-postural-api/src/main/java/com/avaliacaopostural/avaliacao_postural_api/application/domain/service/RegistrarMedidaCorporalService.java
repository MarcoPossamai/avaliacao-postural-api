package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.MedidaJaRegistradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarMedidaCorporalCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarMedidaCorporalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Circunferencia;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.MedidaCorporalRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RegistrarMedidaCorporalService implements RegistrarMedidaCorporalUseCase{
    
    private final MedidaCorporalRepository medidaCorporalRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    @Override 
    @Transactional 
    public MedidaCorporal registrar(RegistrarMedidaCorporalCommand command){
        avaliacaoRepository.buscarPorId(command.avaliacaoId())
            .orElseThrow(() -> new AvaliacaoNaoEncontradaException(command.avaliacaoId()));

        if(medidaCorporalRepository.buscarPorAvaliacao(command.avaliacaoId()).isPresent()){
            throw new MedidaJaRegistradaException(command.avaliacaoId());
        }

        List<Circunferencia> circunferencias = command.circunferencias() == null ? List.of() : command.circunferencias().stream()
            .map(c -> new Circunferencia(c.tipo(), c.valorCm())).toList();
        
        MedidaCorporal medida = new MedidaCorporal(null, command.avaliacaoId(), command.peso(), command.altura(), 
            command.percentualGordura(), circunferencias);
        
        return medidaCorporalRepository.salvar(medida);
    }
}
