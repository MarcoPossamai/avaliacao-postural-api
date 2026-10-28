ALTER TABLE execucoes_treino ADD COLUMN ficha_id BIGINT;
ALTER TABLE execucoes_treino ADD COLUMN exercicio_id BIGINT;

UPDATE execucoes_treino e
SET ficha_id = i.ficha_id,
    exercicio_id = i.exercicio_id
FROM itens_ficha i
WHERE e.item_ficha_id = i.id;

ALTER TABLE execucoes_treino ALTER COLUMN ficha_id SET NOT NULL;
ALTER TABLE execucoes_treino ALTER COLUMN exercicio_id SET NOT NULL;
ALTER TABLE execucoes_treino ALTER COLUMN item_ficha_id DROP NOT NULL;

ALTER TABLE execucoes_treino DROP CONSTRAINT fk_execucoes_item,
    ADD CONSTRAINT fk_execucoes_item FOREIGN KEY (item_ficha_id) REFERENCES itens_ficha (id) ON DELETE SET NULL;

ALTER TABLE execucoes_treino
    ADD CONSTRAINT fk_execucoes_ficha FOREIGN KEY (ficha_id) REFERENCES fichas (id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_execucoes_exercicio FOREIGN KEY (exercicio_id) REFERENCES exercicios (id);
