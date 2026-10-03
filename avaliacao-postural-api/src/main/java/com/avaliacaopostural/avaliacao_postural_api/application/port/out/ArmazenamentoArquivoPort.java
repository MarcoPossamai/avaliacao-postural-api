package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

public interface ArmazenamentoArquivoPort {
    String salvar(String nomeOriginal, byte[] conteudo);
    byte[] ler(String caminhoArquivo);
    void remover(String caminhoArquivo);
}
