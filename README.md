# 📚 Biblioteca API

API REST desenvolvida em Spring Boot para gerenciamento de livros e autores, com autenticação JWT, controle de permissões por role, paginação e documentação interativa via Swagger.

## 🛠️ Tecnologias

- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- MySQL
- Maven
- Swagger (springdoc-openapi)

## ✨ Funcionalidades

- CRUD completo de Livros e Autores
- Autenticação via JWT (login e cadastro de usuário)
- Controle de acesso por roles (`ADMIN` e `USER`)
    - `GET`: liberado para qualquer usuário autenticado
    - `POST`, `PUT`, `DELETE`: restrito a usuários `ADMIN`
- Paginação nas listagens de Livros e Autores
- Documentação interativa da API via Swagger UI

## 🚀 Como rodar o projeto

### Pré-requisitos
- Java 17+ instalado
- MySQL instalado e rodando
- Maven (ou usar o wrapper `mvnw` incluso no projeto)

### Passos

1. Clone o repositório
```bash
git clone https://github.com/lerzinhopeixoto/biblioteca-api.git
```

2. Crie um banco de dados MySQL chamado `biblioteca`

3. Configure o `src/main/resources/application.properties` com as credenciais do seu MySQL local

4. Rode o projeto:
```bash
./mvnw spring-boot:run
```

5. A API estará disponível em `http://localhost:8080`

## 📖 Documentação da API (Swagger)

Com o projeto rodando, acesse:
http://localhost:8080/swagger-ui/index.html



## 🔑 Autenticação

1. Cadastre um usuário em `POST /cadastro`
2. Faça login em `POST /login` para receber o token JWT
3. Use o token no header `Authorization: Bearer <seu-token>` nas requisições protegidas

## 📋 Principais Endpoints

| Método | Rota | Acesso |
|--------|------|--------|
| POST | /cadastro | Público |
| POST | /login | Público |
| GET | /livros | Autenticado |
| POST | /livros | ADMIN |
| PUT | /livros/{id} | ADMIN |
| DELETE | /livros/{id} | ADMIN |
| GET | /autores | Autenticado |
| POST | /autores | ADMIN |
| PUT | /autores/{id} | ADMIN |
| DELETE | /autores/{id} | ADMIN |