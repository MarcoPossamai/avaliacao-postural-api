package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.AlunoNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.EmailJaCadastradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarAlunoCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarAlunoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.InstrutorRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class EditarAlunoService implements EditarAlunoUseCase{
    
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;

    @Override 
    @Transactional 
    public Aluno editar(EditarAlunoCommand command){
        Aluno atual = alunoRepository.buscarPorId(command.alunoId())
            .orElseThrow(() -> new AlunoNaoEncontradoException(command.alunoId()));

        if (!command.email().equals(atual.getEmail()) && alunoRepository.existePorEmail(command.email()) || instrutorRepository.existePorEmail(command.email())) {
            throw new EmailJaCadastradoException(command.email());
        }

        Aluno atualizado = new Aluno(atual.getId(), command.nome(), command.email(), atual.getSenha(), 
            command.telefone(), command.sexo(), command.dataNascimento(), command.objetivos(), command.observacoes());
        return alunoRepository.salvar(atualizado);
    }
}
