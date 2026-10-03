package com.avaliacaopostural.avaliacao_postural_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {
    
    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/error").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/alunos", "/api/instrutores").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.POST, "/api/alunos/*/avaliacoes").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.POST, "/api/alunos/*/avaliacoes").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.POST, "/api/avaliacoes/*/medida").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.POST, "/api/avaliacoes/*/fotografias").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.DELETE, "/api/avaliacoes/*/fotografias/*").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.POST, "/api/avaliacoes/*/desvios").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.PUT, "/api/avaliacoes/*/desvios/*").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.DELETE, "/api/avaliacoes/*/desvios/*").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.GET, "/api/alunos").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.DELETE, "/api/alunos/*").hasRole("INSTRUTOR")
            .requestMatchers(HttpMethod.PUT, "/api/alunos/me").hasRole("ALUNO")
            .requestMatchers(HttpMethod.PUT, "/api/instrutores/me").hasRole("INSTRUTOR")
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(conversorDePerfil())));
        
        return http.build();
    }

    private JwtAuthenticationConverter conversorDePerfil(){
        JwtGrantedAuthoritiesConverter authorities = new JwtGrantedAuthoritiesConverter();
        authorities.setAuthoritiesClaimName("perfil");
        authorities.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter conversor = new JwtAuthenticationConverter();
        conversor.setJwtGrantedAuthoritiesConverter(authorities);
        return conversor;
    }
}
