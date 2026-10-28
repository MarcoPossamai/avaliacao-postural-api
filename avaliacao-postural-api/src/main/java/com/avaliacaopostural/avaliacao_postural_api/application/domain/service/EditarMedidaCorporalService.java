package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AlunoNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Circunferencia;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DobrasCutaneas;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarMedidaCorporalUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RegistrarMedidaCorporalCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.MedidaCorporalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditarMedidaCorporalService implements EditarMedidaCorporalUseCase{

    private final MedidaCorporalRepository medidaCorporalRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final AlunoRepository alunoRepository;

    @Override
    @Transactional
    public MedidaCorporal editar(RegistrarMedidaCorporalCommand command){
        Avaliacao avaliacao = avaliacaoRepository.buscarPorId(command.avaliacaoId())
            .orElseThrow(() -> new AvaliacaoNaoEncontradaException(command.avaliacaoId()));

        Aluno aluno = alunoRepository.buscarPorId(avaliacao.getAlunoId())
            .orElseThrow(() -> new AlunoNaoEncontradoException(avaliacao.getAlunoId()));

        Long idAtual = medidaCorporalRepository.buscarPorAvaliacao(command.avaliacaoId())
            .map(MedidaCorporal::getId)
            .orElse(null);

        List<Circunferencia> circunferencias = command.circunferencias() == null ? List.of() : command.circunferencias().stream()
            .map(c -> new Circunferencia(c.tipo(), c.valorCm())).toList();
        List<DobrasCutaneas> dobrasCutaneas = command.dobraCutaneas() == null ? List.of() : command.dobraCutaneas().stream()
            .map(d -> new DobrasCutaneas(d.tipo(), d.valorMm())).toList();

        Integer idade = aluno.getDataNascimento() == null ? null
            : Period.between(aluno.getDataNascimento(), avaliacao.getDataAvaliacao()).getYears();
        Double percentualGordura = MedidaCorporal.calcularPercentualGordura(
            aluno.getSexo(), idade, command.peso(), command.altura(), dobrasCutaneas);

        MedidaCorporal medida = new MedidaCorporal(idAtual, command.avaliacaoId(), command.peso(), command.altura(),
            percentualGordura, circunferencias, dobrasCutaneas);
        return medidaCorporalRepository.salvar(medida);
    }
}
