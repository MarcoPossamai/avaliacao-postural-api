package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ArquivoInvalidoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AvaliacaoNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.LimiteDeFotografiasExcedidoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Fotografia;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AdicionarFotografiaCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AdicionarFotografiaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ArmazenamentoArquivoPort;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AvaliacaoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FotografiaRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AdicionarFotografiaService implements AdicionarFotografiaUseCase{
    
    private static final int LIMITE_FOTOGRAFIAS = 17;
    private static final Set<String> TIPOS_PERMITIDOS = Set.of("image/jpeg", "image/png");

    private final FotografiaRepository fotografiaRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ArmazenamentoArquivoPort armazenamentoArquivoPort;

    @Override 
    @Transactional 
    public Fotografia adicionar(AdicionarFotografiaCommand command){
        avaliacaoRepository.buscarPorId(command.avaliacaoId())
            .orElseThrow(() -> new AvaliacaoNaoEncontradaException(command.avaliacaoId()));
        
        if (!TIPOS_PERMITIDOS.contains(command.contentType())) {
            throw new ArquivoInvalidoException("apenas imagens JPEG ou PNG são aceitas");
        }

        if (fotografiaRepository.contarPorAvaliacao(command.avaliacaoId()) >= LIMITE_FOTOGRAFIAS) {
            throw new LimiteDeFotografiasExcedidoException(command.avaliacaoId());
        }

        String caminho = armazenamentoArquivoPort.salvar(command.nomeOriginal(), command.conteudo());

        Fotografia fotografia = new Fotografia(null, command.avaliacaoId(), caminho, 
            command.contentType(), LocalDateTime.now());
        
        return fotografiaRepository.salvar(fotografia);
    }
}
