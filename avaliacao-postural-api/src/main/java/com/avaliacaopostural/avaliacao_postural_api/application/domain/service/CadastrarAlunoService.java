package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.EmailJaCadastradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarAlunoCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarAlunoUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.InstrutorRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.SenhaCriptografador;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CadastrarAlunoService implements CadastrarAlunoUseCase{

    private final AlunoRepository alunoRepository;
    private final InstrutorRepository instrutorRepository;
    private final SenhaCriptografador senhaCriptografador;

    @Override 
    @Transactional 
    public Aluno cadastrar(CadastrarAlunoCommand command){
        if (alunoRepository.existePorEmail(command.email()) || instrutorRepository.existePorEmail(command.email())) {
            throw new EmailJaCadastradoException(command.email());
        }

        Aluno aluno = new Aluno(
            null,
            command.nome(),
            command.email(),
            senhaCriptografador.criptografar(command.senha()),
            command.telefone(),
            command.sexo(),
            command.dataNascimento(),
            command.objetivos(),
            command.observacoes()
        );

        return alunoRepository.salvar(aluno);
    }
    
}