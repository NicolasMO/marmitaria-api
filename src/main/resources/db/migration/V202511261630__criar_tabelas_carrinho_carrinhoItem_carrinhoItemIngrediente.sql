CREATE TABLE carrinho (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE carrinho_item (
    id BIGSERIAL PRIMARY KEY,
    carrinho_id BIGINT NOT NULL REFERENCES carrinho(id) ON DELETE CASCADE,
    produto_id BIGINT NOT NULL REFERENCES produto(id),
    quantidade INT NOT null,
    observacao VARCHAR(255)
);

CREATE TABLE carrinho_item_ingrediente (
    carrinho_item_id BIGINT NOT NULL REFERENCES carrinho_item(id) ON DELETE CASCADE,
    ingrediente_id BIGINT NOT NULL REFERENCES ingrediente(id),
    PRIMARY KEY (carrinho_item_id, ingrediente_id)
);