package com.avaliacaopostural.avaliacao_postural_api.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class AlunoPersistenceAdapter implements AlunoRepository{
    
    private final AlunoJpaRepository alunoJpaRepository;

    @Override 
    public Aluno salvar(Aluno aluno){
        AlunoJpaEntity entity = toJpaEntity(aluno);
        AlunoJpaEntity salvo = alunoJpaRepository.save(entity);
        return toDomain(salvo);
    }

    @Override 
    public Optional<Aluno> buscarPorId(Long id){
        return alunoJpaRepository.findById(id).map(this::toDomain);
    }

    @Override 
    public Optional<Aluno> buscarPorEmail(String email){
        return alunoJpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override 
    public boolean existePorEmail(String email){
        return alunoJpaRepository.existsByEmail(email);
    }

    @Override 
    public void remover(Long id){
        alunoJpaRepository.deleteById(id);
    }

    @Override 
    public List<Aluno> listarTodos(){
        return alunoJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    private AlunoJpaEntity toJpaEntity(Aluno aluno){
        return AlunoJpaEntity.builder()
            .id(aluno.getId())
            .nome(aluno.getNome())
            .email(aluno.getEmail())
            .senha(aluno.getSenha())
            .telefone(aluno.getTelefone())
            .sexo(aluno.getSexo())
            .dataNascimento(aluno.getDataNascimento())
            .objetivos(aluno.getObjetivos())
            .observacoes(aluno.getObservacoes())
            .build();
    }

    private Aluno toDomain(AlunoJpaEntity entity){
        return new Aluno(
            entity.getId(),
            entity.getNome(),
            entity.getEmail(),
            entity.getSenha(),
            entity.getTelefone(),
            entity.getSexo(),
            entity.getDataNascimento(),
            entity.getObjetivos(),
            entity.getObservacoes()
        );
    }
}
