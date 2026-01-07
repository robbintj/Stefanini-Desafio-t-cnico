# 📋 Stefanini Todo List - Sistema de Gerenciamento de Tarefas

![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen?style=flat&logo=spring)
![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Build](https://img.shields.io/badge/build-passing-brightgreen.svg)

Sistema completo de gerenciamento de tarefas desenvolvido como desafio técnico, com backend em Spring Boot e documentação interativa via Swagger.

---

## 📑 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Execução](#-instalação-e-execução)
- [Testando a API](#-testando-a-api)
- [Documentação da API](#-documentação-da-api)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Funcionalidades](#-funcionalidades)
- [Banco de Dados](#-banco-de-dados)
- [Deploy](#-deploy)
- [Contribuindo](#-contribuindo)

---

## 🎯 Sobre o Projeto

O **Stefanini Todo List** é uma aplicação RESTful para gerenciamento de tarefas que permite criar, listar, atualizar e deletar tarefas (CRUD completo). O projeto foi desenvolvido seguindo as melhores práticas de desenvolvimento, com foco em:

- ✅ Clean Code e SOLID
- ✅ Arquitetura em camadas
- ✅ Tratamento robusto de exceções
- ✅ Validação de dados
- ✅ Documentação automática (Swagger/OpenAPI)
- ✅ Testes unitários
- ✅ CI/CD Pipeline

### 🎨 Características Principais

- **CRUD Completo**: Criar, listar, atualizar e deletar tarefas
- **Filtros**: Buscar tarefas por status (PENDENTE, EM_ANDAMENTO, CONCLUIDA)
- **Validações**: Validação automática de dados de entrada
- **Exceções**: Sistema robusto de tratamento de erros
- **Documentação**: Swagger UI para teste interativo
- **Database**: H2 in-memory para desenvolvimento, SQL Server para produção
- **Migrations**: Flyway para controle de versão do banco

---

## 🚀 Tecnologias

### Backend

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| Java | 21 LTS | Linguagem de programação |
| Spring Boot | 3.2.1 | Framework principal |
| Spring Data JPA | 3.2.1 | Persistência de dados |
| Spring Validation | 3.2.1 | Validação de beans |
| Hibernate | 6.4.x | ORM |
| Flyway | 9.x | Migrations de banco |
| H2 Database | 2.x | Banco in-memory (dev) |
| SQL Server | - | Banco de produção |
| Lombok | 1.18.34 | Redução de boilerplate |
| ModelMapper | 3.2.0 | Mapeamento de objetos |
| SpringDoc OpenAPI | 2.3.0 | Documentação Swagger |
| Maven | 3.9+ | Gerenciamento de dependências |

### Ferramentas de Desenvolvimento

- **Docker**: Containerização
- **GitHub Actions**: CI/CD
- **JaCoCo**: Cobertura de código
- **JUnit 5**: Testes unitários
- **Git**: Controle de versão

---

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas (Layered Architecture) com separação clara de responsabilidades:

```
┌─────────────────────────────────────────────┐
│           Controllers Layer                 │
│   (Recebe requisições HTTP)                 │
└──────────────┬──────────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────────┐
│           Application Layer                 │
│   (Services, DTOs, Use Cases)               │
└──────────────┬──────────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────────┐
│            Domain Layer                     │
│   (Entities, Repositories, Enums)           │
└──────────────┬──────────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────────┐
│        Infrastructure Layer                 │
│   (Config, Exceptions, Controllers)         │
└─────────────────────────────────────────────┘
```

### Padrões de Projeto Utilizados

- ✅ **Repository Pattern**: Abstração da camada de dados
- ✅ **DTO Pattern**: Transferência de dados entre camadas
- ✅ **Builder Pattern**: Construção de objetos complexos
- ✅ **Dependency Injection**: Inversão de controle
- ✅ **Exception Handler**: Tratamento centralizado de exceções

---

## ⚙️ Pré-requisitos

Antes de começar, certifique-se de ter instalado:

### Obrigatório

- ☕ **Java 21** (JDK 21 LTS)
  ```bash
  java -version
  # Deve mostrar: openjdk version "21.x.x"
  ```

- 📦 **Maven 3.9+**
  ```bash
  mvn -version
  # Deve mostrar: Apache Maven 3.9.x
  ```

### Opcional

- 🐳 **Docker** (para executar via container)
- 📮 **Postman** (para testar API)
- 💻 **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### Instalação do Java 21

#### macOS
```bash
# Usando Homebrew
brew install openjdk@21

# Ou usando SDKMAN
sdk install java 21-tem
sdk use java 21-tem
```

#### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

#### Windows
Baixe o instalador do [Adoptium](https://adoptium.net/temurin/releases/?version=21)

---

## 🔧 Instalação e Execução

### Opção 1: Execução Rápida (Recomendado)

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/stefanini-todolist.git
cd stefanini-todolist/backend

# 2. Execute o script (já compila e roda)
./run.sh
```

A aplicação estará disponível em: **http://localhost:8080**

### Opção 2: Passo a Passo Manual

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/stefanini-todolist.git
cd stefanini-todolist/backend

# 2. Compile o projeto
mvn clean install

# 3. Execute a aplicação
mvn spring-boot:run

# Ou execute o JAR diretamente
java -jar target/todolist-1.0.0.jar
```

### Opção 3: Usando Docker

```bash
# 1. Build da imagem
docker build -t stefanini/todolist-backend .

# 2. Execute o container
docker run -p 8080:8080 stefanini/todolist-backend

# 3. Verifique se está rodando
docker ps
```

### Opção 4: Docker Compose (Recomendado para produção)

```bash
# Crie um arquivo docker-compose.yml
docker-compose up -d
```

**docker-compose.yml**:
```yaml
version: '3.8'

services:
  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    healthcheck:
      test: ["CMD", "wget", "--quiet", "--tries=1", "--spider", "http://localhost:8080/api/tarefas"]
      interval: 30s
      timeout: 3s
      retries: 3
```

---

## 🧪 Testando a API

### 1. Verificar se a aplicação está rodando

```bash
curl http://localhost:8080/api/tarefas
```

Resposta esperada: `[]` (lista vazia) ou lista de tarefas

### 2. Swagger UI (Recomendado) 📚

Acesse no navegador:
```
http://localhost:8080/swagger-ui.html
```

O Swagger UI oferece:
- ✅ Documentação interativa completa
- ✅ Teste de todos os endpoints direto no navegador
- ✅ Exemplos de request/response
- ✅ Validação automática
- ✅ Não precisa de ferramentas externas

### 3. Usando Postman 📮

#### Importar Collection

Crie uma collection no Postman com os seguintes endpoints:

**Base URL**: `http://localhost:8080/api`

#### Endpoints Disponíveis

##### 1️⃣ Listar Todas as Tarefas
```http
GET http://localhost:8080/api/tarefas
```

##### 2️⃣ Buscar Tarefa por ID
```http
GET http://localhost:8080/api/tarefas/1
```

##### 3️⃣ Listar Tarefas por Status
```http
GET http://localhost:8080/api/tarefas/status/PENDENTE
```
Valores válidos: `PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`

##### 4️⃣ Criar Nova Tarefa
```http
POST http://localhost:8080/api/tarefas
Content-Type: application/json

{
  "titulo": "Implementar testes unitários",
  "descricao": "Criar testes para todos os services",
  "status": "PENDENTE"
}
```

##### 5️⃣ Atualizar Tarefa
```http
PUT http://localhost:8080/api/tarefas/1
Content-Type: application/json

{
  "titulo": "Implementar testes unitários - Atualizado",
  "descricao": "Criar testes unitários e de integração",
  "status": "EM_ANDAMENTO"
}
```

##### 6️⃣ Deletar Tarefa
```http
DELETE http://localhost:8080/api/tarefas/1
```

### 4. Usando cURL (Terminal)

#### Criar tarefa
```bash
curl -X POST http://localhost:8080/api/tarefas \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Minha primeira tarefa",
    "descricao": "Testando a API",
    "status": "PENDENTE"
  }'
```

#### Listar tarefas
```bash
curl http://localhost:8080/api/tarefas
```

#### Atualizar tarefa
```bash
curl -X PUT http://localhost:8080/api/tarefas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Tarefa atualizada",
    "descricao": "Descrição atualizada",
    "status": "CONCLUIDA"
  }'
```

#### Deletar tarefa
```bash
curl -X DELETE http://localhost:8080/api/tarefas/1
```

### 5. Executar Testes Automatizados

```bash
# Executar todos os testes
mvn test

# Ver relatório de cobertura
mvn jacoco:report
open target/site/jacoco/index.html
```

---

## 📖 Documentação da API

### Swagger/OpenAPI

A documentação completa está disponível via Swagger UI:

🔗 **URL**: http://localhost:8080/swagger-ui.html

📄 **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### Endpoints

| Método | Endpoint | Descrição | Status Code |
|--------|----------|-----------|-------------|
| GET | `/api/tarefas` | Lista todas as tarefas | 200 |
| GET | `/api/tarefas/{id}` | Busca tarefa por ID | 200, 404 |
| GET | `/api/tarefas/status/{status}` | Lista por status | 200 |
| POST | `/api/tarefas` | Cria nova tarefa | 201, 400 |
| PUT | `/api/tarefas/{id}` | Atualiza tarefa | 200, 404, 400 |
| DELETE | `/api/tarefas/{id}` | Deleta tarefa | 204, 404 |

### Modelos de Dados

#### TarefaCreateDTO (Request - POST)
```json
{
  "titulo": "string (3-100 caracteres) *obrigatório",
  "descricao": "string (max 500 caracteres)",
  "status": "PENDENTE | EM_ANDAMENTO | CONCLUIDA"
}
```

#### TarefaUpdateDTO (Request - PUT)
```json
{
  "titulo": "string (3-100 caracteres) *obrigatório",
  "descricao": "string (max 500 caracteres)",
  "status": "PENDENTE | EM_ANDAMENTO | CONCLUIDA *obrigatório"
}
```

#### TarefaResponseDTO (Response)
```json
{
  "id": 1,
  "titulo": "string",
  "descricao": "string",
  "status": "PENDENTE | EM_ANDAMENTO | CONCLUIDA",
  "dataCriacao": "2026-01-07T10:30:00",
  "dataAtualizacao": "2026-01-07T10:30:00"
}
```

### Respostas de Erro

#### Erro de Validação (400)
```json
{
  "timestamp": "2026-01-07T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Erro de validação nos dados da requisição",
  "path": "/api/tarefas",
  "validationErrors": [
    {
      "field": "titulo",
      "message": "O título é obrigatório",
      "rejectedValue": null
    }
  ]
}
```

#### Recurso Não Encontrado (404)
```json
{
  "timestamp": "2026-01-07T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Tarefa não encontrada com ID: 999",
  "path": "/api/tarefas/999"
}
```

---

## 📁 Estrutura do Projeto

```
backend/
├── .github/
│   ├── workflows/
│   │   └── ci-cd.yml              # Pipeline CI/CD
│   ├── CI-CD-PIPELINE.md          # Doc do pipeline
│   └── QUICK-START.md             # Guia rápido
│
├── src/
│   ├── main/
│   │   ├── java/com/stefanini/todolist/
│   │   │   ├── TodoListApplication.java      # Classe principal
│   │   │   │
│   │   │   ├── application/                  # Camada de Aplicação
│   │   │   │   ├── dtos/                     # Data Transfer Objects
│   │   │   │   │   ├── TarefaCreateDTO.java
│   │   │   │   │   ├── TarefaUpdateDTO.java
│   │   │   │   │   └── TarefaResponseDTO.java
│   │   │   │   │
│   │   │   │   ├── services/                 # Interfaces de Serviço
│   │   │   │   │   ├── TarefaService.java
│   │   │   │   │   └── TarefaServiceImpl.java
│   │   │   │   │
│   │   │   │   └── usecases/                 # Casos de Uso
│   │   │   │
│   │   │   ├── domain/                       # Camada de Domínio
│   │   │   │   ├── entities/                 # Entidades JPA
│   │   │   │   │   └── Tarefa.java
│   │   │   │   │
│   │   │   │   ├── enums/                    # Enumerações
│   │   │   │   │   └── StatusTarefa.java
│   │   │   │   │
│   │   │   │   └── repositories/             # Repositórios
│   │   │   │       └── TarefaRepository.java
│   │   │   │
│   │   │   └── infrastructure/               # Camada de Infraestrutura
│   │   │       ├── config/                   # Configurações
│   │   │       │   ├── ModelMapperConfig.java
│   │   │       │   └── SwaggerConfig.java
│   │   │       │
│   │   │       ├── controllers/              # Controllers REST
│   │   │       │   └── TarefaController.java
│   │   │       │
│   │   │       └── exceptions/               # Tratamento de Exceções
│   │   │           ├── GlobalExceptionHandler.java
│   │   │           ├── ErrorResponse.java
│   │   │           ├── ResourceNotFoundException.java
│   │   │           ├── BusinessRuleException.java
│   │   │           └── InvalidDataException.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties        # Configurações da aplicação
│   │       ├── application-test.properties   # Config de testes
│   │       └── db/migration/                 # Migrations Flyway
│   │           ├── V1__criar_tabela_tarefas.sql
│   │           └── V2__inserir_dados_iniciais.sql
│   │
│   └── test/
│       └── java/com/stefanini/todolist/
│           ├── services/
│           │   └── TarefaServiceImplTest.java
│           └── infrastructure/exceptions/
│               └── CustomExceptionsTest.java
│
├── target/                         # Build artifacts
├── .gitignore                      # Git ignore
├── Dockerfile                      # Docker build
├── pom.xml                         # Maven config
├── build.sh                        # Script de build
├── run.sh                          # Script de execução
├── README.md                       # Este arquivo
├── EXCEPTION_HANDLING.md           # Doc de exceções
├── JAVA-21-MIGRATION.md           # Doc Java 21
└── INDEX.md                        # Índice de docs
```

---

## ⚡ Funcionalidades

### ✅ Implementadas

- [x] **CRUD Completo de Tarefas**
  - Criar tarefa
  - Listar todas as tarefas
  - Buscar tarefa por ID
  - Listar tarefas por status
  - Atualizar tarefa
  - Deletar tarefa

- [x] **Validações**
  - Título obrigatório (3-100 caracteres)
  - Descrição opcional (max 500 caracteres)
  - Status válido (PENDENTE, EM_ANDAMENTO, CONCLUIDA)

- [x] **Tratamento de Exceções**
  - Erro 404 (Recurso não encontrado)
  - Erro 400 (Validação falhou)
  - Erro 422 (Regra de negócio violada)
  - Erro 500 (Erro interno)

- [x] **Documentação**
  - Swagger UI interativo
  - OpenAPI 3.0
  - JavaDoc completo

- [x] **Testes**
  - Testes unitários (13 testes)
  - Cobertura de código (JaCoCo)
  - 100% de sucesso

- [x] **DevOps**
  - Docker support
  - CI/CD Pipeline (GitHub Actions)
  - Scripts de build e execução

### 🔮 Futuras Melhorias

- [ ] Autenticação e Autorização (Spring Security)
- [ ] Paginação de resultados
- [ ] Filtros avançados (por data, título, etc)
- [ ] Tags/Categorias para tarefas
- [ ] Prioridade de tarefas
- [ ] Data de vencimento
- [ ] Anexos de arquivos
- [ ] Notificações
- [ ] Frontend (React/Angular/Vue)
- [ ] WebSocket para atualizações em tempo real

---

## 🗄️ Banco de Dados

### Desenvolvimento (H2 In-Memory)

Por padrão, a aplicação usa H2 Database em memória para facilitar o desenvolvimento:

```properties
spring.datasource.url=jdbc:h2:mem:todolist
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

**Console H2**: http://localhost:8080/h2-console

**Credenciais**:
- JDBC URL: `jdbc:h2:mem:todolist`
- Username: `sa`
- Password: *(vazio)*

### Produção (SQL Server)

Para usar SQL Server em produção, configure as propriedades:

```properties
# application-prod.properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=todolist
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
```

Execute com:
```bash
java -jar todolist-1.0.0.jar --spring.profiles.active=prod
```

### Migrations (Flyway)

As migrations são executadas automaticamente na inicialização:

1. **V1__criar_tabela_tarefas.sql**: Cria a tabela principal
2. **V2__inserir_dados_iniciais.sql**: Insere dados de exemplo

```sql
-- Estrutura da tabela
CREATE TABLE tarefas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    status VARCHAR(20) NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    data_atualizacao TIMESTAMP NOT NULL
);
```

---

## 🚢 Deploy

### Heroku

```bash
# Login no Heroku
heroku login

# Criar app
heroku create stefanini-todolist

# Deploy
git push heroku main

# Abrir app
heroku open
```

### AWS Elastic Beanstalk

```bash
# Criar JAR
mvn clean package

# Deploy
eb init -p java-21 stefanini-todolist
eb create stefanini-todolist-env
eb deploy
```

### Docker Hub

```bash
# Build
docker build -t seu-usuario/todolist-backend .

# Push
docker login
docker push seu-usuario/todolist-backend:latest

# Pull e Run
docker pull seu-usuario/todolist-backend:latest
docker run -p 8080:8080 seu-usuario/todolist-backend:latest
```

### Kubernetes

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: todolist-backend
spec:
  replicas: 3
  selector:
    matchLabels:
      app: todolist
  template:
    metadata:
      labels:
        app: todolist
    spec:
      containers:
      - name: backend
        image: stefanini/todolist-backend:latest
        ports:
        - containerPort: 8080
```

---

## 🧑‍💻 Desenvolvimento

### Executar em Modo de Desenvolvimento

```bash
# Com hot reload
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Ou com o script
./run.sh
```

### Executar Testes

```bash
# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=TarefaServiceImplTest

# Com cobertura
mvn clean test jacoco:report
```

### Verificar Cobertura de Código

```bash
mvn jacoco:report
open target/site/jacoco/index.html
```

### Formatar Código

```bash
# Verificar estilo
mvn checkstyle:check

# Formatar (se configurado)
mvn formatter:format
```

### Gerar Documentação

```bash
# JavaDoc
mvn javadoc:javadoc
open target/site/apidocs/index.html
```

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'feat: Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

### Padrões de Commit

Seguimos o [Conventional Commits](https://www.conventionalcommits.org/):

- `feat`: Nova funcionalidade
- `fix`: Correção de bug
- `docs`: Documentação
- `style`: Formatação
- `refactor`: Refatoração
- `test`: Testes
- `chore`: Manutenção

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👥 Autores

- **Stefanini Group** - *Desafio Técnico*
- **GitHub Copilot** - *Implementação*

---

## 📞 Suporte

### Documentação Adicional

- 📄 [Exception Handling](EXCEPTION_HANDLING.md) - Sistema de exceções
- 📄 [Java 21 Migration](JAVA-21-MIGRATION.md) - Migração Java 21
- 📄 [CI/CD Pipeline](.github/CI-CD-PIPELINE.md) - Pipeline
- 📄 [Quick Start](.github/QUICK-START.md) - Comandos rápidos
- 📄 [Index](INDEX.md) - Índice de documentação

### Problemas Comuns

#### Erro: Java version não compatível
```bash
# Verificar versão
java -version

# Instalar Java 21
sdk install java 21-tem
sdk use java 21-tem
```

#### Erro: Port 8080 já em uso
```bash
# Mudar porta
mvn spring-boot:run -Dserver.port=8081

# Ou no application.properties
server.port=8081
```

#### Erro: Testes falhando
```bash
# Limpar build
mvn clean

# Recompilar
mvn clean install
```

---

## 🎯 Quick Start (TL;DR)

```bash
# Clone
git clone https://github.com/seu-usuario/stefanini-todolist.git
cd stefanini-todolist/backend

# Execute
./run.sh

# Acesse
# API: http://localhost:8080/api/tarefas
# Swagger: http://localhost:8080/swagger-ui.html
# H2 Console: http://localhost:8080/h2-console
```

---

## 📊 Status do Projeto

```
✅ Backend API:         100% Completo
✅ Documentação:        100% Completo
✅ Testes:              13/13 Passing
✅ CI/CD:               Configurado
✅ Docker:              Pronto
✅ Swagger:             Ativo
```

---

## 🌟 Agradecimentos

Obrigado por conferir este projeto! Se você achou útil, considere dar uma ⭐ no repositório.

**Happy Coding! 🚀**

---

**Última atualização:** 07 de Janeiro de 2026  
**Versão:** 1.0.0  
**Java:** 21 LTS

