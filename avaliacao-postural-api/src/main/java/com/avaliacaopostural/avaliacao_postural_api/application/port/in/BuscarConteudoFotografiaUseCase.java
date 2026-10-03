package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

public interface BuscarConteudoFotografiaUseCase {
    ConteudoArquivo buscarConteudo(Long avaliacaoId, Long fotografiaId);
}
