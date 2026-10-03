ALTER TABLE avaliacoes DROP CONSTRAINT fk_avaliacoes_aluno,
    ADD CONSTRAINT fk_avaliacoes_aluno FOREIGN KEY (aluno_id) REFERENCES alunos (id) ON DELETE CASCADE;

ALTER TABLE medidas_corporais DROP CONSTRAINT fk_medidas_avaliacao,
    ADD CONSTRAINT fk_medidas_avaliacao FOREIGN KEY (avaliacao_id) REFERENCES avaliacoes (id) ON DELETE CASCADE;

ALTER TABLE medida_circunferencias DROP CONSTRAINT fk_circunferencias_medida,
    ADD CONSTRAINT fk_circunferencias_medida FOREIGN KEY (medida_corporal_id) REFERENCES medidas_corporais (id) ON DELETE CASCADE;

ALTER TABLE fotografias DROP CONSTRAINT fk_fotografias_avaliacao,
    ADD CONSTRAINT fk_fotografias_avaliacao FOREIGN KEY (avaliacao_id) REFERENCES avaliacoes (id) ON DELETE CASCADE;

ALTER TABLE desvios_posturais DROP CONSTRAINT fk_desvios_avaliacao,
    ADD CONSTRAINT fk_desvios_avaliacao FOREIGN KEY (avaliacao_id) REFERENCES avaliacoes (id) ON DELETE CASCADE;