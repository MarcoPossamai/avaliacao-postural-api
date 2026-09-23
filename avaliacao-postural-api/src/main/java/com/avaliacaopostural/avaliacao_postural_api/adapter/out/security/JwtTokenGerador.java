package com.avaliacaopostural.avaliacao_postural_api.adapter.out.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Perfil;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.TokenGerador;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class JwtTokenGerador implements TokenGerador{
    
    private final JwtEncoder jwtEncoder;

    @Value ("${app.jwt.expiracao-minutos}")
    private long expiracaoMinutos;

    @Override 
    public String gerar(Long usuarioId, String email, Perfil perfil){
        Instant agora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
            .subject(String.valueOf(usuarioId))
            .claim("email", email)
            .claim("perfil", perfil.name())
            .issuedAt(agora)
            .expiresAt(agora.plus(expiracaoMinutos, ChronoUnit.MINUTES))
            .build();
        
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
