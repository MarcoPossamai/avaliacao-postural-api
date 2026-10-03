package com.avaliacaopostural.avaliacao_postural_api.adapter.in.web.dto;

import java.time.LocalDate;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Instrutor;

public record InstrutorResponse(
    Long id,
    String nome, 
    String email, 
    String telefone,
    String sexo,
    LocalDate dataNascimento,
    String cref
) {
    public static InstrutorResponse from(Instrutor instrutor){
        return new InstrutorResponse(instrutor.getId(), instrutor.getNome(), instrutor.getEmail(), 
        instrutor.getTelefone(), instrutor.getSexo(), instrutor.getDataNascimento(), instrutor.getCref());
    }
}
