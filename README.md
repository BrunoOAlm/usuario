# 👤 Microsserviço de Usuário

Este projeto faz parte de um sistema distribuído de Agendador de Tarefas baseado em arquitetura de microsserviços.

Ele é responsável pelo gerenciamento de usuários dentro do sistema.

---

## 📌 Sobre o projeto

Este microsserviço tem como objetivo centralizar todas as regras de negócio relacionadas a usuários, como cadastro, consulta e validação de dados.

Ele funciona de forma independente e pode se comunicar com outros serviços através de APIs.

---

## 📌 Responsabilidades

- Cadastro de usuários
- Consulta de usuários
- Validação de dados do usuário
- Persistência no banco de dados
- Exposição de API REST para consumo de outros serviços

---

## 📌 Arquitetura do sistema

Frontend → BFF → Microsserviços  
Este serviço faz parte do conjunto de microsserviços do sistema Agendador de Tarefas.

---

## 📌 Tecnologias utilizadas

- Java 21 
- Spring Boot  
- Spring Data JPA  
- PostgreSQL  
- Gradle  
- Git / GitHub  
-Docker
-Documentado pelo Swagger

## 📌 Estrutura do projeto


controller/
service/
repository/
entity/
dto/


---

## 📌 Objetivo do projeto

Este microsserviço foi desenvolvido para praticar arquitetura de microsserviços, com foco em separação de responsabilidades e independência entre serviços.

---

## 📌 Autor

Desenvolvido por Bruno Almeida  
Projeto de estudo em microsserviços e backend Java
