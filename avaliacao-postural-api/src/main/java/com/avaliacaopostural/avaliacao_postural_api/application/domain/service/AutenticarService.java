package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.CredenciaisInvalidasException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Aluno;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Instrutor;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Perfil;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Usuario;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AutenticacaoResult;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AutenticarCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.AutenticarUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.AlunoRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.InstrutorRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.SenhaCriptografador;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.TokenGerador;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class AutenticarService implements AutenticarUseCase{
    
    private final InstrutorRepository instrutorRepository;
    private final AlunoRepository alunoRepository;
    private final SenhaCriptografador senhaCriptografador;
    private final TokenGerador tokenGerador;

    @Override 
    public AutenticacaoResult autenticar(AutenticarCommand command){
        Optional<Instrutor> instrutor = instrutorRepository.buscarPorEmail(command.email());
        if (instrutor.isPresent()) {
            validarSenha(command.senha(), instrutor.get().getSenha());
            return resultado(instrutor.get(), Perfil.INSTRUTOR);
        }

        Aluno aluno = alunoRepository.buscarPorEmail(command.email())
            .orElseThrow(CredenciaisInvalidasException::new);
        validarSenha(command.senha(), aluno.getSenha());
        return resultado(aluno, Perfil.ALUNO);
    }

    private void validarSenha(String informada, String hash){
        if(!senhaCriptografador.confere(informada, hash)){
            throw new CredenciaisInvalidasException();
        }
    }

    private AutenticacaoResult resultado(Usuario usuario, Perfil perfil){
        String token = tokenGerador.gerar(usuario.getId(), usuario.getEmail(), perfil);
        return new AutenticacaoResult(token, perfil, usuario.getNome());
    }
}
