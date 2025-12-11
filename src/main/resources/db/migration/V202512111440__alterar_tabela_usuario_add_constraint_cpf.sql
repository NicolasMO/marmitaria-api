ALTER TABLE usuario
    ADD COLUMN ativo BOOLEAN NOT NULL DEFAULT false;

ALTER TABLE usuario
    ALTER COLUMN celular SET NOT NULL;

ALTER TABLE usuario
    ALTER COLUMN cpf SET NOT NULL;

ALTER TABLE usuario
    ADD CONSTRAINT chk_usuario_cpf_digits CHECK (cpf ~ '^[0-9]{11}$');