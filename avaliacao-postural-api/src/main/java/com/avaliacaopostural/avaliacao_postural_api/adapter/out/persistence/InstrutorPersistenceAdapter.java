package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Instrutor;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.InstrutorRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class InstrutorPersistenceAdapter implements InstrutorRepository{

    private final InstrutorJpaRepository instrutorJpaRepository;

    @Override 
    public Instrutor salvar(Instrutor instrutor){
        InstrutorJpaEntity salvo = instrutorJpaRepository.save(toJpaEntity(instrutor));
        return toDomain(salvo);
    }

    @Override 
    public Optional<Instrutor> buscarPorId(Long id){
        return instrutorJpaRepository.findById(id).map(this::toDomain);
    }

    @Override 
    public Optional<Instrutor> buscarPorEmail(String email){
        return instrutorJpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override 
    public boolean existePorEmail(String email){
        return instrutorJpaRepository.existsByEmail(email);
    }

    private InstrutorJpaEntity toJpaEntity(Instrutor instrutor){
        return InstrutorJpaEntity.builder()
            .id(instrutor.getId())
            .nome(instrutor.getNome())
            .email(instrutor.getEmail())
            .senha(instrutor.getSenha())
            .telefone(instrutor.getTelefone())
            .sexo(instrutor.getSexo())
            .dataNascimento(instrutor.getDataNascimento())
            .cref(instrutor.getCref())
            .build();
    }

    private Instrutor toDomain(InstrutorJpaEntity entity){
        return new Instrutor(
            entity.getId(),
            entity.getNome(),
            entity.getEmail(),
            entity.getSenha(),
            entity.getTelefone(),
            entity.getSexo(),
            entity.getDataNascimento(),
            entity.getCref()
        );
    }
    
}
