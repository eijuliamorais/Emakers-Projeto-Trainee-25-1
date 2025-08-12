# 📚 Sistema de Gerenciamento de Biblioteca

Este projeto é uma API REST desenvolvida em **Spring Boot** para gerenciar livros, pessoas e empréstimos em uma biblioteca.

## 🚀 Funcionalidades

- **CRUD de Livro** – cadastrar, listar, atualizar e excluir livros.  
- **CRUD de Pessoa** – cadastrar, listar, atualizar e excluir pessoas.  
- **Relacionamento Pessoa ↔ Livro** – relação muitos-para-muitos (M:N).  
- **Empréstimo de Livro** – uma pessoa pode pegar um ou mais livros emprestados.  
- **Devolução de Livro** – registrar devoluções de forma simples.  
- **Autenticação** – sistema de login seguro usando **Spring Security**.  
- **Consumo de API Externa** – integração com a API ViaCep para preenchimento automático de endereço.  
- **Documentação da API com Swagger** – endpoints documentados e acessíveis via interface web.  
- **Tratamento de Exceções** – respostas claras para erros, com códigos HTTP adequados.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17+**  
- **Spring Boot**  
  - Spring Web  
  - Spring Data JPA  
  - Spring Security  
- **Banco de Dados**: PostgreSQL  
- **Swagger** para documentação  
- **Lombok** para simplificação do código  
- **API ViaCep** para busca de endereços

---
## ⚙️ Configuração e Execução

1. Clonar o repositório
```bash
https://github.com/eijuliamorais/Emakers-Projeto-Trainee-25-1.git
 ```
2. Configurar o banco de dados no arquivo application.properties:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca_api
spring.datasource.username=root
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
 ```
3. Executar a aplicação
```bash
mvn spring-boot:run
 ```

4. Acessar o Swagger
```bash
http://localhost:8080/swagger-ui.html
 ```
---

## 🔒 Autenticação

- O sistema utiliza JWT (JSON Web Token) para autenticação.
- Primeiro, faça login e receba um token.
- Em seguida, envie este token no cabeçalho Authorization: Bearer <token> para acessar rotas protegidas.

---

## 🌐 API Externa
Integração com a API ViaCep para preenchimento automático de endereço a partir do CEP informado.
