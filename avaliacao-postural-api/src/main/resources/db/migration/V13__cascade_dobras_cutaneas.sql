ALTER TABLE medida_dobras_cutaneas DROP CONSTRAINT fk_dobras_medida;
ALTER TABLE medida_dobras_cutaneas ADD CONSTRAINT fk_dobras_medida
    FOREIGN KEY (medida_corporal_id) REFERENCES medidas_corporais(id) ON DELETE CASCADE;
