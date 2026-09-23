package com.avaliacaopostural.avaliacao_postural_api.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration 
public class JwtConfig {
    
    @Bean 
    public JwtEncoder jwtEncoder(@Value("${app.jwt.secret}") String secret){
        return new NimbusJwtEncoder(new ImmutableSecret<>(chave(secret)));
    }

    @Bean 
    public JwtDecoder jwtDecoder(@Value("${app.jwt.secret}") String secret){
        return NimbusJwtDecoder.withSecretKey(chave(secret))
            .macAlgorithm(MacAlgorithm.HS256)
            .build();
    }

    private SecretKey chave(String secret){
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("app.jwt.secret precisa ter no mínimo 32 caracteres");
        }

        return new SecretKeySpec(bytes, "HmacSHA256");
    }
}
