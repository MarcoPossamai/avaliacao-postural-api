package com.avaliacaopostural.avaliacao_postural_api.adapter.out.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ArmazenamentoArquivoPort;


@Component 
public class ArmazenamentoLocalAdapter implements ArmazenamentoArquivoPort{
    
    private final Path diretorioBase;
    
    public ArmazenamentoLocalAdapter(@Value ("${app.storage.diretorio}") String diretorio){
        this.diretorioBase = Paths.get(diretorio).toAbsolutePath().normalize();
        try {
            Files.createDirectories(diretorioBase);
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível criar o diretório de armazenamento: " + diretorioBase, e);
        }
    }

    @Override 
    public String salvar(String nomeOriginal, byte[] conteudo){
        String nomeArquivo = UUID.randomUUID() + extrairExtensao(nomeOriginal);
        try {
            Files.write(diretorioBase.resolve(nomeArquivo), conteudo);
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao salvar arquivo " + nomeArquivo, e);
        }
        return nomeArquivo;
    }

    @Override 
    public byte[] ler(String caminhoArquivo){
        try {
            return Files.readAllBytes(diretorioBase.resolve(caminhoArquivo));
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao ler arquivo: " + caminhoArquivo, e);
        }
    }

    @Override 
    public void remover(String caminhoArquivo){
        try {
            Files.deleteIfExists(diretorioBase.resolve(caminhoArquivo));
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao remover arquivo: " + caminhoArquivo, e);
        }
    }

    private String extrairExtensao(String nomeOriginal){
        if (nomeOriginal == null) {
            return "";
        }
        int ponto = nomeOriginal.lastIndexOf('.');
        return ponto >= 0 ? nomeOriginal.substring(ponto) : "";
    }
}
