ALTER TABLE fichas ADD COLUMN avaliacao_referencia_id BIGINT;
ALTER TABLE fichas ADD CONSTRAINT fk_fichas_avaliacao_referencia
    FOREIGN KEY (avaliacao_referencia_id) REFERENCES avaliacoes(id) ON DELETE SET NULL;
