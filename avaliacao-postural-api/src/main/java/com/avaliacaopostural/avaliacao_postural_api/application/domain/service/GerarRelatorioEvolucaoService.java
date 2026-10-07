package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AlunoNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.PeriodoInvalidoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Avaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ExecucaoTreino;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.GerarRelatorioEvolucaoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.RelatorioEvolucao;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.DesvioPosturalRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ExecucaoTreinoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.MedidaCorporalRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class GerarRelatorioEvolucaoService implements GerarRelatorioEvolucaoUseCase{
    
    private final AlunoRepository alunoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final MedidaCorporalRepository medidaCorporalRepository;
    private final DesvioPosturalRepository desvioPosturalRepository;
    private final ExecucaoTreinoRepository execucaoTreinoRepository;

    @Override 
    @Transactional (readOnly = true)
    public RelatorioEvolucao gerar(Long alunoId, LocalDate periodoInicio, LocalDate periodoFim){
        alunoRepository.buscarPorId(alunoId).orElseThrow(() -> new AlunoNaoEncontradoException(alunoId));

        if (periodoInicio.isAfter(periodoFim)) {
            throw new PeriodoInvalidoException("a data de início não pode ser depois da data de fim");
        }

        List<Avaliacao> avaliacoesNoPeriodo = avaliacaoRepository.listarPorAluno(alunoId).stream()
            .filter(a -> !a.getDataAvaliacao().isBefore(periodoInicio) && !a.getDataAvaliacao().isAfter(periodoFim)).toList();

        List<RelatorioEvolucao.PontoMedida> evolucaoMedidas = avaliacoesNoPeriodo.stream()
            .map(a -> medidaCorporalRepository.buscarPorAvaliacao(a.getId())
                .map(m -> new RelatorioEvolucao.PontoMedida(a.getDataAvaliacao(), m.getPeso(), m.getImc(), m.getPercentualGordura()))
                .orElse(null))
            .filter(Objects::nonNull)
            .sorted(Comparator.comparing(RelatorioEvolucao.PontoMedida::data))
            .toList();
            
        List<RelatorioEvolucao.PontoDesvio> evolucaoDesvios = avaliacoesNoPeriodo.stream()
            .flatMap(a -> desvioPosturalRepository.listarPorAvaliacao(a.getId()).stream()
                .map(d -> new RelatorioEvolucao.PontoDesvio(a.getDataAvaliacao(), d.getRegiao(), d.getTipo(), d.getSeveridade())))
            .sorted(Comparator.comparing(RelatorioEvolucao.PontoDesvio::data))
            .toList();

        LocalDateTime inicio = periodoInicio.atStartOfDay();
        LocalDateTime fim = periodoFim.atTime(23, 59, 59);
        List<RelatorioEvolucao.PontoCarga> evolucaoCargas = execucaoTreinoRepository.listarPorAluno(alunoId).stream()
            .filter(e -> e.getHoraInicio() != null && !e.getHoraInicio().isBefore(inicio) && !e.getHoraInicio().isAfter(fim))
            .sorted(Comparator.comparing(ExecucaoTreino::getHoraInicio))
            .map(e -> new RelatorioEvolucao.PontoCarga(e.getHoraInicio(), e.getItemFichaId(), e.getCargaUtilizada(), e.getRepeticoes()))
            .toList();

        return new RelatorioEvolucao(evolucaoMedidas, evolucaoDesvios, evolucaoCargas);
    }
}
