package com.avaliacaopostural.avaliacao_postural_api.application.domain.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ExercicioNaoEncontradoException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.FichaNaoEncontradaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.exception.ItemFichaInvalidaException;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.Ficha;
import com.avaliacaopostural.avaliacao_postural_api.application.domain.model.ItemFicha;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarFichaCommand;
import com.avaliacaopostural.avaliacao_postural_api.application.port.in.EditarFichaUseCase;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.ExercicioRepository;
import com.avaliacaopostural.avaliacao_postural_api.application.port.out.FichaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EditarFichaService implements EditarFichaUseCase {

    private final FichaRepository fichaRepository;
    private final ExercicioRepository exercicioRepository;

    @Override
    @Transactional
    public Ficha editar(EditarFichaCommand command) {
        Ficha atual = fichaRepository.buscarPorId(command.fichaId())
                .orElseThrow(() -> new FichaNaoEncontradaException(command.fichaId()));

        Set<Long> idsAtuais = atual.getItens().stream().map(ItemFicha::getId).collect(Collectors.toSet());

        List<ItemFicha> itens = command.itens().stream().map(i -> {
            if (i.id() != null && !idsAtuais.contains(i.id())) {
                throw new ItemFichaInvalidaException("o item " + i.id() + " não pertence a ficha");
            }
            exercicioRepository.buscarPorId(i.exercicioId())
                    .orElseThrow(() -> new ExercicioNaoEncontradoException(i.exercicioId()));
            return new ItemFicha(i.id(), i.exercicioId(), i.ordem(), i.series(), i.repeticoesMin(), i.repeticoesMax(),
                    i.descanso());
        }).toList();

        Ficha atualizada = new Ficha(atual.getId(), atual.getAlunoId(), atual.getInstrutorId(), command.nome(),
                atual.getDataInicio(), command.dataFim(), command.frequenciaSemanal(), command.avaliacaoReferenciaId(),
                itens);
        return fichaRepository.salvar(atualizada);
    }
}
