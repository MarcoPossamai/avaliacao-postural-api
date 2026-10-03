package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ComparacaoIvalidaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Circunferencia;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.DesvioPostural;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.MedidaCorporal;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.VariacaoDesvio;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.VariacaoMedida;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CompararAvaliacoesUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ComparativoAvaliacoes;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.DetalheAvaliacao;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.ObterDetalheAvaliacaoUseCase;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CompararAvaliacoesService implements CompararAvaliacoesUseCase{

    private final ObterDetalheAvaliacaoUseCase obterDetalheAvaliacaoUseCase;
    
    @Override 
    public ComparativoAvaliacoes comparar(Long avaliacaoInicialId, Long avaliacaoFinalId){
        if (avaliacaoInicialId.equals(avaliacaoFinalId)) {
            throw new ComparacaoIvalidaException("as duas avaliações precisam ser diferentes");
        }

        DetalheAvaliacao inicial = obterDetalheAvaliacaoUseCase.obter(avaliacaoInicialId);
        DetalheAvaliacao finalAvaliacao = obterDetalheAvaliacaoUseCase.obter(avaliacaoFinalId);

        if (!inicial.avaliacao().getAlunoId().equals(finalAvaliacao.avaliacao().getAlunoId())) {
            throw new ComparacaoIvalidaException("as avaliações pertencem a alunos diferentes");
        }

        List<VariacaoMedida> variacoes = compararMedidas(inicial.medidaCorporal(), finalAvaliacao.medidaCorporal());
        List<VariacaoDesvio> variacoesDesvios = compararDesvios(inicial.desvios(), finalAvaliacao.desvios());

        return new ComparativoAvaliacoes(inicial, finalAvaliacao, variacoes, variacoesDesvios);
    }

    private List<VariacaoMedida> compararMedidas(MedidaCorporal inicial, MedidaCorporal fim){
        List<VariacaoMedida> variacoes = new ArrayList<>();

        variacoes.add(new VariacaoMedida("Peso (kg)", 
            inicial != null ? inicial.getPeso() : null, fim != null ? fim.getPeso() : null));

        variacoes.add(new VariacaoMedida("IMC", 
            inicial != null ? inicial.getImc() : null, fim != null ? fim.getImc() : null));

        variacoes.add(new VariacaoMedida("Percentual de gordura (%)", 
            inicial != null ? inicial.getPercentualGordura() : null, fim != null ? fim.getPercentualGordura() : null));
        
        for(String tipo : tiposDeCircunferencia(inicial, fim)){
            variacoes.add(new VariacaoMedida("Circunferência - " + tipo, 
                valorCircunferencia(inicial, tipo), valorCircunferencia(fim, tipo)));
        }

        return variacoes;
    }

    private List<VariacaoDesvio> compararDesvios(List<DesvioPostural> inicial, List<DesvioPostural> fim){
        Map<String, DesvioPostural> porChaveInicial = new LinkedHashMap<>();
        inicial.forEach(d -> porChaveInicial.putIfAbsent(chave(d), d));

        Map<String, DesvioPostural> porChaveFinal = new LinkedHashMap<>();
        fim.forEach(d -> porChaveFinal.putIfAbsent(chave(d), d));

        Set<String> chaves = new LinkedHashSet<>(porChaveInicial.keySet());
        chaves.addAll(porChaveFinal.keySet());

        List<VariacaoDesvio> variacoes = new ArrayList<>();
        for(String chave : chaves){
            DesvioPostural a = porChaveInicial.get(chave);
            DesvioPostural b = porChaveFinal.get(chave);
            DesvioPostural ref = a != null ? a : b;
            variacoes.add(new VariacaoDesvio(ref.getRegiao(), ref.getTipo(), 
                a != null ? a.getSeveridade() : null, 
                b != null ? b.getSeveridade() : null));
        }
        return variacoes;
    }

    private String chave(DesvioPostural d){
        return d.getRegiao() + "|" + d.getTipo().trim().toLowerCase(Locale.ROOT);
    }

    private Set<String> tiposDeCircunferencia(MedidaCorporal inicial, MedidaCorporal fim){
        Set<String> tipos = new LinkedHashSet<>();
        if(inicial != null) inicial.getCircunferencias().forEach(c -> tipos.add(c.getTipo()));
        if(fim != null) fim.getCircunferencias().forEach(c -> tipos.add(c.getTipo()));
        return tipos;
    }

    private Double valorCircunferencia(MedidaCorporal medida, String tipo){
        if(medida == null) return null;
        return medida.getCircunferencias().stream()
            .filter(c -> c.getTipo().equals(tipo))
            .map(Circunferencia::getValorCm)
            .findFirst().orElse(null);
    }
}
