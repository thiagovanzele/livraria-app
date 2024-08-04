# Biblioteca Project

## Descrição

O projeto Biblioteca é uma aplicação Java desenvolvida com Spring Boot, que permite a gestão de livros, autores, editoras, resumos e clientes. O sistema é projetado para realizar operações de CRUD (Create, Read, Update, Delete) em livros, autores, editoras, resumos e pedidos de livros.

## Funcionalidades

- **Gerenciamento de Livros**: Adicionar, atualizar, excluir e listar livros.
- **Gerenciamento de Autores**: Adicionar, atualizar, excluir e listar autores.
- **Gerenciamento de Clientes**: Adicionar, atualizar, excluir e listar clientes.
- **Pedidos de Livros**: Criar e gerenciar pedidos de livros.
- **ViaCepApi**: O projeto ultiliza a [API Via Cep](https://viacep.com.br/) para transformar o cep em uma entidade de endereço no BD. 

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

## Configuração do Banco de Dados

Antes de iniciar a aplicação, você precisa configurar as propriedades do banco de dados no arquivo `application.properties`.

### Passos para Configuração

1. **Abra o arquivo `application.properties`** localizado em `src/main/resources`.

2. **Atualize as propriedades do banco de dados** com as informações do seu ambiente PostgreSQL. Substitua os valores de `spring.datasource.url`, `spring.datasource.username`, e `spring.datasource.password` conforme suas configurações:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/livraria-app
   spring.datasource.username=postgres
   spring.datasource.password=

   spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true

   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

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
 
## Exemplos de Requisições HTTP

Aqui estão alguns exemplos de como interagir com a API usando ferramentas como Postman ou cURL:

### **Adicionar um Novo Livro**

- **Requisição POST**:
  ```http
  POST /livros
  Content-Type: application/json

  {
    "titulo" : "Harry Potter e a Pedra Filosofal",
    "resumo" : "Harry Potter, um garoto de 11 anos, descobre que é um bruxo e vai para a Escola de Magia e Bruxaria de Hogwarts.",
    "idEditora" : 1,
    "autoresIds" : [1],
    "preco" : 35.29
  }
  ```

### **Retorno da Requisição:**

```http
   {
  "id": 2,
  "titulo": "Harry Potter e a Pedra Filosofal",
  "resumo": {
    "id": 1,
    "comentario": "Harry Potter, um garoto de 11 anos, descobre que é um bruxo e vai para a Escola de Magia e Bruxaria de Hogwarts."
  },
  "editora": {
    "id": 1,
    "nome": "Bloomsbury Publishing"
  },
  "autores": [
    {
      "id": 1,
      "nome": "J. K. Rowling"
    }
  ],
  "preco": 35.29
  }
  ```

  ### **Adicionar um Novo Cliente**

- **Requisição POST**:
  ```http
  POST /clientes
  Content-Type: application/json

  {
    "nome": "João Silva",
    "documento": "12345678901",
    "email": "joao.silva@example.com",
    "cep": "01001000",
    "numero": "123"
  }
  ```

  ### **Retorno da Requisição:**
```http
  {
    "id": 1,
    "nome": "João Silva",
    "documento": "12345678901",
    "email": "joao.silva@example.com",
    "endereco": {
        "id": 1,
        "cep": "01001000",
        "complemento": null,
        "rua": "Praça da Sé",
        "bairro": "Sé",
        "cidade": "São Paulo",
        "estado": "SP",
        "numero": "123"
    }
  }
  ```
  ### **Adicionar um novo pedido**

- **Requisição POST**:
  ```http
  POST /pedidos
  Content-Type: application/json

  {
    "data" : "2024-08-04T15:30:00",
    "itens" : [{
        "livroId" : 2,
        "quantidade" : 3
    }],
    "clienteId" : 1
  }
  ```

### **Retorno da Requisição:**

 ```http
 {
    "id": 1,
    "data": "2024-08-04T15:30:00",
    "cliente": {
        "id": 1,
        "nome": "João Silva",
        "documento": "12345678901",
        "email": "joao.silva@example.com",
        "endereco": {
            "id": 1,
            "cep": "01001000",
            "complemento": null,
            "rua": "Praça da Sé",
            "bairro": "Sé",
            "cidade": "São Paulo",
            "estado": "SP",
            "numero": "123"
        }
    },
    "itens": [
        {
            "quantidade": 3,
            "preco": 105.87,
            "livro": {
                "id": 2,
                "titulo": "Harry Potter e a Pedra Filosofal",
                "resumo": {
                    "id": 1,
                    "comentario": "Harry Potter, um garoto de 11 anos, descobre que é um bruxo e vai para a Escola de Magia e Bruxaria de Hogwarts."
                },
                "editora": {
                    "id": 1,
                    "nome": "Bloomsbury Publishing"
                },
                "autores": [
                    {
                        "id": 1,
                        "nome": "J. K. Rowling"
                    }
                ],
                "preco": 35.29
            },
            "subTotal": 105.87
        }
    ],
    "total": 105.87
}
  ```

### **Resgatar um Livro pelo ID**

- **Requisição POST**:
  ```http
  GET /livros/3
  Content-Type: application/json
  ```

### **Retorno da Requisição:**

```http
   {
    "id": 3,
    "titulo": "Memorias Postumas de Brás Cubas",
    "resumo": {
        "id": 2,
        "comentario": "Um romance que narra a vida de Brás Cubas, um defunto-autor que conta sua história a partir de sua perspectiva após a morte."
    },
    "editora": {
        "id": 2,
        "nome": "Tipografia Nacional"
    },
    "autores": [
        {
            "id": 2,
            "nome": "Machado de Assis"
        }
    ],
    "preco": 25.19
}
  ```

  ### **Resgatar um Cliente pelo ID**

- **Requisição POST**:
  ```http
  GET /clientes/2
  Content-Type: application/json
  ```

  ### **Retorno da Requisição:**
```http
 {
    "id": 2,
    "nome": "Maria Joana",
    "documento": "45678912345",
    "email": "maria.joana@example.com",
    "endereco": {
        "id": 2,
        "cep": "26413410",
        "complemento": null,
        "rua": "Rua Adalgisa",
        "bairro": "Cidade Jardim Marajoara",
        "cidade": "Japeri",
        "estado": "RJ",
        "numero": "456"
    }
}
  ```
  ### **Resgatar um Pedido pelo ID**

- **Requisição POST**:
  ```http
  GET /pedidos/3
  Content-Type: application/json
  ```

### **Retorno da Requisição:**

 ```http
   {
    "id": 2,
    "data": "2024-08-02T10:30:00",
    "cliente": {
        "id": 2,
        "nome": "Maria Joana",
        "documento": "45678912345",
        "email": "maria.joana@example.com",
        "endereco": {
            "id": 2,
            "cep": "26413410",
            "complemento": null,
            "rua": "Rua Adalgisa",
            "bairro": "Cidade Jardim Marajoara",
            "cidade": "Japeri",
            "estado": "RJ",
            "numero": "456"
        }
    },
    "itens": [
        {
            "quantidade": 1,
            "preco": 35.29,
            "livro": {
                "id": 2,
                "titulo": "Harry Potter e a Pedra Filosofal",
                "resumo": {
                    "id": 1,
                    "comentario": "Harry Potter, um garoto de 11 anos, descobre que é um bruxo e vai para a Escola de Magia e Bruxaria de Hogwarts."
                },
                "editora": {
                    "id": 1,
                    "nome": "Bloomsbury Publishing"
                },
                "autores": [
                    {
                        "id": 1,
                        "nome": "J. K. Rowling"
                    }
                ],
                "preco": 35.29
            },
            "subTotal": 35.29
        },
        {
            "quantidade": 1,
            "preco": 25.19,
            "livro": {
                "id": 3,
                "titulo": "Memorias Postumas de Brás Cubas",
                "resumo": {
                    "id": 2,
                    "comentario": "Um romance que narra a vida de Brás Cubas, um defunto-autor que conta sua história a partir de sua perspectiva após a morte."
                },
                "editora": {
                    "id": 2,
                    "nome": "Tipografia Nacional"
                },
                "autores": [
                    {
                        "id": 2,
                        "nome": "Machado de Assis"
                    }
                ],
                "preco": 25.19
            },
            "subTotal": 25.19
        }
    ],
    "total": "60,48"
}
  ```

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo `LICENSE` para detalhes.

## Contato

Se você tiver alguma dúvida ou sugestão, entre em contato:

**Thiago Vanzele** - thiagovanzele@gmail.com  
[LinkedIn - Thiago Vanzele](https://www.linkedin.com/in/thiagovanzele)



