package com.avaliacaopostural.avaliacao_postural_api.adapter.out.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.port.out.SenhaCriptografador;

@Component 
public class BCryptSenhaCriptografador implements SenhaCriptografador{
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override 
    public String criptografar(String senhaEmTextoPuro){
        return encoder.encode(senhaEmTextoPuro);
    }

    @Override 
    public boolean confere(String senhaEmTextoPuro, String senhaCriptografada){
        return encoder.matches(senhaEmTextoPuro, senhaCriptografada);
    }

}
