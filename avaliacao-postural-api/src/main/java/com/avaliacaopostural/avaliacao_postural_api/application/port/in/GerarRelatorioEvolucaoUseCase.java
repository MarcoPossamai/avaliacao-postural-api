package com.avaliacaopostural.avaliacao_postural_api.application.port.in;

import java.time.LocalDate;

public interface GerarRelatorioEvolucaoUseCase {
    RelatorioEvolucao gerar(Long alunoId, LocalDate periodoInicio, LocalDate periodoFim);
}
