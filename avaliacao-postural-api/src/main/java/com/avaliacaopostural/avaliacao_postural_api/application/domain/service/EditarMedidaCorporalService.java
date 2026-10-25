package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Circunferencia;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DobrasCutaneas;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarMedidaCorporalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarMedidaCorporalCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.MedidaCorporalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditarMedidaCorporalService implements EditarMedidaCorporalUseCase{

    private final MedidaCorporalRepository medidaCorporalRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    @Override
    @Transactional
    public MedidaCorporal editar(RegistrarMedidaCorporalCommand command){
        avaliacaoRepository.buscarPorId(command.avaliacaoId())
            .orElseThrow(() -> new AvaliacaoNaoEncontradaException(command.avaliacaoId()));

        Long idAtual = medidaCorporalRepository.buscarPorAvaliacao(command.avaliacaoId())
            .map(MedidaCorporal::getId)
            .orElse(null);

        List<Circunferencia> circunferencias = command.circunferencias() == null ? List.of() : command.circunferencias().stream()
            .map(c -> new Circunferencia(c.tipo(), c.valorCm())).toList();
        List<DobrasCutaneas> dobrasCutaneas = command.dobraCutaneas() == null ? List.of() : command.dobraCutaneas().stream()
            .map(d -> new DobrasCutaneas(d.tipo(), d.valorMm())).toList();

        MedidaCorporal medida = new MedidaCorporal(idAtual, command.avaliacaoId(), command.peso(), command.altura(),
            command.percentualGordura(), circunferencias, dobrasCutaneas);
        return medidaCorporalRepository.salvar(medida);
    }
}
