package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

import java.util.Optional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Instrutor;

public interface InstrutorRepository {
    
    Instrutor salvar(Instrutor instrutor);

    Optional<Instrutor> buscarPorId(Long id);

    Optional<Instrutor> buscarPorEmail(String email);

    boolean existePorEmail(String email);
}
