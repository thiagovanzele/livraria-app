# Biblioteca Project

## Descrição

O projeto Biblioteca é uma aplicação Java desenvolvida com Spring Boot, que permite a gestão de livros, autores, editoras, resumos e clientes. O sistema é projetado para realizar operações de CRUD (Create, Read, Update, Delete) em livros, autores, editoras, resumos e pedidos de livros.

## Funcionalidades

- **Gerenciamento de Livros**: Adicionar, atualizar, excluir e listar livros.
- **Gerenciamento de Autores**: Adicionar, atualizar, excluir e listar autores.
- **Gerenciamento de Clientes**: Adicionar, atualizar, excluir e listar clientes.
- **Pedidos de Livros**: Criar e gerenciar pedidos de livros.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para desenvolvimento do backend.
- **Spring Data JPA**: Para gerenciamento de persistência e banco de dados.
- **H2 Database**: Banco de dados em memória para testes.
- **PostgreSQL**: Banco de dados para desenvovimento.
- **Maven**: Gerenciador de dependências e build.

## Pré-requisitos

Antes de começar, você precisará ter os seguintes softwares instalados:

- [Java JDK 17 ou superior](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

## Instalação

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/thiagovanzele/livraria-app.git
    ```
2. **Navegue até o diretório do projeto:**:

   ```bash
   cd livraria-app
      ```
3. **Compile e execute o projeto:**

   ```bash
   mvn clean install
   mvn spring-boot:run
     ```

   ## Uso

Após iniciar a aplicação, você pode acessar a API REST através do endpoint:

- **Base URL**: `http://localhost:8080`

### Endpoints

- **Livros**:
  - `GET /livros` - Listar todos os livros.
  - `POST /livros` - Adicionar um novo livro.
  - `PUT /livros/{id}` - Atualizar um livro existente.
  - `DELETE /livros/{id}` - Excluir um livro.

- **Autores**:
  - `GET /autores` - Listar todos os autores.
  - `POST /autores` - Adicionar um novo autor.
  - `PUT /autores/{id}` - Atualizar um autor existente.
  - `DELETE /autores/{id}` - Excluir um autor.

- **Clientes**:
  - `GET /clientes` - Listar todos os clientes.
  - `POST /clientes` - Adicionar um novo cliente.
  - `PUT /clientes/{id}` - Atualizar um cliente existente.
  - `DELETE /clientes/{id}` - Excluir um cliente.

- **Pedidos**:
  - `POST /pedidos` - Criar um novo pedido.
 
- **Item Pedido**:
  - `POST /itens-pedido` - Criar um novo item de pedido.
