package com.avaliacaopostural.avaliacao_postural_api.application.port.out;

public interface SenhaCriptografador {
    
    String criptografar(String senhaEmTextoPuro);

    boolean confere(String senhaEmTextoPuro, String senhaCriptografada);
}
