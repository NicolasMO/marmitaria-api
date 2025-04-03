API da Marmitaria (Em desenvolvimento)

Este repositório contém o **back-end** da aplicação de uma marmitaria, desenvolvido com **Java 17 e Spring Boot 3**. Ele fornece os endpoints necessários para gerenciar usuários, produtos, carrinho de compras e pedidos.  

 Link para o repositório do front-end: [marmitaria-web](https://github.com/NicolasMO/marmitaria-web)  

 
Tecnologias Utilizadas  
Java 17  
Spring Boot 3  
JPA / Hibernate  
Lombok  
Spring Security  
Banco de Dados PostgresSQL  
CORS Configurado para Front-end.  

Funcionalidades implementadas até o momento  
✅ Autenticação e login de usuários  
✅ Cadastro e listagem de produtos  
✅ Adição, remoção e modificação de produtos do carrinho  
✅ Montagem de marmitas personalizadas  
✅ Regras de negócio para cálculo de valores  

Funcionalidades em Desenvolvimento  
🔄 Finalização de pedidos  
🔄 Cadastro de novos usuários  

Antes de começar, você precisará ter os seguintes itens instalados:  
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)  
- [Maven](https://maven.apache.org/download.cgi)  
- [PostgreSQL](https://www.postgresql.org/download/)  

**Clone o repositório:** 
git clone https://github.com/seu-usuario/marmitaria-backend.git

```sql
Configure o banco de dados:  
spring.datasource.url=jdbc:postgresql://localhost:5432/marmitaria  
spring.datasource.username=seu_usuario  
spring.datasource.password=sua_senha  
spring.jpa.hibernate.ddl-auto=update  
```
O servidor estará disponível em http://localhost:8080

Principais endpoints  
___
Cadastro de Usuário
POST /usuario
```json
{
  "nome": "usuario",
  "senha": "senha123",
  "email": "usuario@example.com",
  "celular": "99999999999"
}
```
---
Buscar Usuário 

GET /usuario → Retorna todos os usuários

GET /usuario/{id} → Retorna um usuário específico

---
Listar Produtos e Ingredientes

GET /produtos → Lista todos os produtos

GET /ingredientes → Lista todos os ingredientes

---

Adicionar Item ao Carrinho
POST /carrinho/adicionar
```json
{
  "usuarioId": 2,
  "produtoId": 5,
  "quantidade": 2
}
  ```

Adicionar Marmita ao Carrinho
POST /carrinho/adicionar

```json
{
  "usuarioId": 2,
  "produtoId": 1,
  "quantidade": 1,
  "marmitaDTO": {
    "ingredientesId": []
} 
```
  ---
Listar Itens do Carrinho

GET /carrinho/{usuarioId}

Limpar Carrinho

DELETE /carrinho/{usuarioId}

Remover Item do Carrinho

DELETE /carrinho/{usuarioId}/{itemId}

---
Autenticação (Login)
POST /auth/login
  ```json
    "email": "usuario@example.com",
    "senha": "senha123"
```
⚠ Importante: Algumas ações, como adicionar itens ao carrinho, exigem que o usuário esteja autenticado.


Necessário popular o banco com os seguintes dados antes de iniciar o projeto:

```sql
-- Produtos
INSERT INTO produto (nome, preco, tipo, imagem) VALUES
('Marmita Pequena', 12.0, 'MARMITA_PEQUENA', 'marmitapequena.jpg'),
('Marmita Grande', 16.0, 'MARMITA_GRANDE', 'marmitagrande.jpg'),
('Coca-Cola Lata', 5.0, 'BEBIDA', 'cocalata.jpg'),
('Coca Zero Lata', 6.0, 'BEBIDA', 'cocazerolata.jpg'),
('Fanta Laranja Lata', 5.0, 'BEBIDA', 'fantalata.jpg'),
('Guaraná Lata', 5.0, 'BEBIDA', 'guaranalata.jpg'),
('Água Mineral', 3.0, 'BEBIDA', 'agua.jpg');


-- Ingredientes

INSERT INTO ingrediente (categoria, nome) VALUES
('PROTEINA', 'Peito de frango assado'),
('PROTEINA', 'Carne trinchada'),
('PROTEINA', 'Omelete c/ frango e queijo'),
('PROTEINA', 'Almondegas'),
('PROTEINA', 'Bife de Soja'),
('CARBOIDRATO', 'Arroz branco'),
('CARBOIDRATO', 'Baião'),
('CARBOIDRATO', 'Feijão Carioca'),
('CARBOIDRATO', 'Macarrão ao alho e óleo'),
('CARBOIDRATO', 'Arroz integral'),
('COMPLEMENTO', 'Batata cozida'),
('COMPLEMENTO', 'Vinagrete'),
('COMPLEMENTO', 'Repolho refogado'),
('COMPLEMENTO', 'Batata doce cozida'),
('COMPLEMENTO', 'Suflê de xuxu'),
('COMPLEMENTO', 'Salada verde'),
('COMPLEMENTO', 'Cenoura ralada'),
('COMPLEMENTO', 'Legumes com maionese'),
('COMPLEMENTO', 'Farofa de ovo'),
('COMPLEMENTO', 'Abóbora cozida');
