CREATE TABLE medida_dobras_cutaneas(
    medida_corporal_id BIGINT NOT NULL,
    tipo VARCHAR(255),
    valor_mm DOUBLE PRECISION,
    CONSTRAINT fk_dobras_medida FOREIGN KEY (medida_corporal_id) REFERENCES medidas_corporais(id)
);