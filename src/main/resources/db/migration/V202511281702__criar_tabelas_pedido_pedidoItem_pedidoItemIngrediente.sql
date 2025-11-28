CREATE TABLE pedido (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    data_pedido TIMESTAMP NOT NULL DEFAULT NOW(),
    total NUMERIC(10,2),
    endereco_entrega VARCHAR(255),
    forma_pagamento VARCHAR(50),
    status VARCHAR(50)
);

CREATE TABLE pedido_item (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL REFERENCES pedido(id) ON DELETE CASCADE,
    produto_id BIGINT NOT NULL REFERENCES produto(id),
    quantidade INT NOT NULL,
    observacao VARCHAR(255)
);


CREATE TABLE pedido_item_ingrediente (
    pedido_item_id BIGINT NOT NULL REFERENCES pedido_item(id) ON DELETE CASCADE,
    ingrediente_id BIGINT NOT NULL REFERENCES ingrediente(id),
    PRIMARY KEY (pedido_item_id, ingrediente_id)
);