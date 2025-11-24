CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    celular VARCHAR(20),
    cpf VARCHAR(11) UNIQUE
);

CREATE TABLE endereco (
    id SERIAL PRIMARY KEY,
    usuario_id INT NOT NULL REFERENCES usuario(id) ON DELETE CASCADE,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(20),
    cidade VARCHAR(150) NOT NULL,
    bairro VARCHAR(150),
    estado VARCHAR(100),
    complemento VARCHAR(255),
    cep VARCHAR(20)
);

CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco_unitario NUMERIC(6,2) NOT NULL,
    imagem VARCHAR(255),
    tipo VARCHAR(20) NOT NULL
);

CREATE TABLE ingrediente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    categoria VARCHAR(20) NOT NULL
);