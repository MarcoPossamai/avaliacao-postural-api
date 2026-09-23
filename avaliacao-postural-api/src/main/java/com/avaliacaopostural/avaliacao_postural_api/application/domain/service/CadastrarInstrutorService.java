package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.EmailJaCadastradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Instrutor;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarInstrutorCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.CadastrarInstrutorUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.InstrutorRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.SenhaCriptografador;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CadastrarInstrutorService implements CadastrarInstrutorUseCase{
    
    private final InstrutorRepository instrutorRepository;
    private final AlunoRepository alunoRepository;
    private final SenhaCriptografador senhaCriptografador;

    @Override 
    @Transactional 
    public Instrutor cadastrar(CadastrarInstrutorCommand command){
        if(instrutorRepository.existePorEmail(command.email()) || alunoRepository.existePorEmail(command.email())){
            throw new EmailJaCadastradoException(command.email());
        }

        Instrutor instrutor = new Instrutor(
            null,
            command.nome(),
            command.email(),
            senhaCriptografador.criptografar(command.senha()),
            command.telefone(),
            command.sexo(),
            command.dataNascimento(),
            command.cref()
        );

        return instrutorRepository.salvar(instrutor);
    }
}
