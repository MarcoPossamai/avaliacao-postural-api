package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.EmailJaCadastradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.InstrutorNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Instrutor;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarInstrutorCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarInstrutorUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.InstrutorRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class EditarInstrutorService implements EditarInstrutorUseCase{
    
    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;

    @Override 
    @Transactional 
    public Instrutor editar(EditarInstrutorCommand command){
        Instrutor atual = instrutorRepository.buscarPorId(command.instrutorId())
            .orElseThrow(() -> new InstrutorNaoEncontradoException(command.instrutorId()));

        if (!command.email().equals(atual.getEmail()) && instrutorRepository.existePorEmail(command.email()) || alunoRepository.existePorEmail(command.email())) {
            throw new EmailJaCadastradoException(command.email());
        }

        Instrutor atualizado = new Instrutor(atual.getId(), command.nome(), command.email(), atual.getSenha(), 
            command.telefone(), command.sexo(), command.dataNascimento(), command.cref());
        return instrutorRepository.salvar(atualizado);
    }
}
