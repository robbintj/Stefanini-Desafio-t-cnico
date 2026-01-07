# Sistema de Gerenciamento de Tarefas (To-Do List)

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-green)
![Angular](https://img.shields.io/badge/Angular-19-red)
![SQL Server](https://img.shields.io/badge/SQL%20Server-2019+-blue)
![License](https://img.shields.io/badge/License-Apache%202.0-blue)

## 📋 Sobre o Projeto

Sistema completo de gerenciamento de tarefas desenvolvido como parte do **Desafio Técnico Stefanini**. A aplicação permite criar, listar, editar e excluir tarefas, seguindo as melhores práticas de desenvolvimento com **Clean Code**, **Clean Architecture** e **SOLID**.

### 🎯 Funcionalidades

- ✅ Criar novas tarefas com título, descrição e status
- 📝 Listar todas as tarefas cadastradas
- 🔍 Filtrar tarefas por status (Pendente, Em Andamento, Concluída)
- ✏️ Editar tarefas existentes
- 🗑️ Excluir tarefas
- 📊 Visualização organizada com cards
- 🎨 Interface responsiva e moderna

### 🏗️ Arquitetura

O projeto segue os princípios de **Clean Architecture**, organizado em camadas:

#### Backend
```
📦 backend
├── 📂 domain (Camada de Domínio)
│   ├── entities      # Entidades JPA
│   ├── enums        # Enumerações
│   └── repositories # Interfaces de repositório
├── 📂 application (Camada de Aplicação)
│   ├── dtos         # Data Transfer Objects
│   └── services     # Lógica de negócio
└── 📂 infrastructure (Camada de Infraestrutura)
    ├── controllers  # REST Controllers
    ├── config       # Configurações
    └── exceptions   # Tratamento de exceções
```

#### Frontend
```
📦 frontend
├── 📂 models       # Interfaces TypeScript
├── 📂 services     # Serviços HTTP
└── 📂 components   # Componentes Angular
```

---

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.2.1** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **SQL Server** - Banco de dados relacional
- **Flyway** - Migrations de banco de dados
- **Lombok** - Redução de código boilerplate
- **ModelMapper** - Mapeamento de objetos
- **Swagger/OpenAPI** - Documentação da API
- **JUnit 5** - Testes unitários
- **Mockito** - Mocks para testes
- **Maven** - Gerenciamento de dependências

### Frontend
- **Angular 19** - Framework frontend
- **TypeScript** - Linguagem de programação
- **RxJS** - Programação reativa
- **SCSS** - Estilização
- **Standalone Components** - Arquitetura moderna do Angular

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- ☕ **Java 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- 📦 **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
- 🗄️ **SQL Server 2019+** ou **SQL Server Express** - [Download](https://www.microsoft.com/sql-server/sql-server-downloads)
- 🟢 **Node.js 18+** - [Download](https://nodejs.org/)
- 📱 **Angular CLI 19** - Instalado via npm
- 🔧 **Git** - [Download](https://git-scm.com/)

---

## 🛠️ Instalação e Configuração

### 1️⃣ Configuração do Banco de Dados SQL Server

#### Opção A: SQL Server Express (Local)

1. **Baixar e instalar SQL Server Express:**
   - Acesse: https://www.microsoft.com/sql-server/sql-server-downloads
   - Baixe a versão Express (gratuita)
   - Durante a instalação, escolha "Basic" ou "Custom"
   - Anote a instância criada (geralmente `localhost` ou `localhost\SQLEXPRESS`)

2. **Habilitar autenticação SQL Server:**
   ```sql
   -- Abra SQL Server Management Studio (SSMS) ou Azure Data Studio
   -- Execute os comandos abaixo:
   
   -- Habilitar modo de autenticação mista
   USE master;
   GO
   EXEC xp_instance_regwrite N'HKEY_LOCAL_MACHINE', 
        N'Software\Microsoft\MSSQLServer\MSSQLServer',
        N'LoginMode', REG_DWORD, 2;
   GO
   
   -- Reinicie o serviço SQL Server após executar
   ```

3. **Criar usuário e banco de dados:**
   ```sql
   -- Criar login SA (se ainda não existir)
   ALTER LOGIN sa WITH PASSWORD = 'SuaSenha@123';
   ALTER LOGIN sa ENABLE;
   GO
   
   -- Criar banco de dados
   CREATE DATABASE todolist_db;
   GO
   ```

4. **Configurar Firewall (se necessário):**
   - Porta padrão: **1433**
   - Libere no Windows Firewall se estiver usando conexão remota

#### Opção B: Docker (Recomendado para desenvolvimento)

```bash
# Executar SQL Server em container Docker
docker run -e "ACCEPT_EULA=Y" -e "SA_PASSWORD=SuaSenha@123" \
   -p 1433:1433 --name sqlserver \
   -d mcr.microsoft.com/mssql/server:2019-latest

# Verificar se está rodando
docker ps

# Acessar o container (opcional)
docker exec -it sqlserver /opt/mssql-tools/bin/sqlcmd \
   -S localhost -U sa -P 'SuaSenha@123'
```

### 2️⃣ Configuração do Backend (Spring Boot)

1. **Clone o repositório:**
   ```bash
   git clone <url-do-repositorio>
   cd Stefanini-Desafio-t-cnico/backend
   ```

2. **Configure o arquivo `application.properties`:**
   ```properties
   # Edite: backend/src/main/resources/application.properties
   
   # Ajuste a URL de conexão conforme sua instalação
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=todolist_db;encrypt=true;trustServerCertificate=true
   
   # Ajuste usuário e senha
   spring.datasource.username=sa
   spring.datasource.password=SuaSenha@123
   ```

3. **Compile o projeto:**
   ```bash
   mvn clean install
   ```

4. **Execute a aplicação:**
   ```bash
   mvn spring-boot:run
   ```

5. **Verifique se está rodando:**
   - API: http://localhost:8080/api/tarefas
   - Swagger: http://localhost:8080/swagger-ui.html

### 3️⃣ Configuração do Frontend (Angular)

1. **Navegue até o diretório frontend:**
   ```bash
   cd ../frontend
   ```

2. **Instale as dependências:**
   ```bash
   npm install
   ```

3. **Execute a aplicação:**
   ```bash
   npm start
   # ou
   ng serve
   ```

4. **Acesse a aplicação:**
   - URL: http://localhost:4200

---

## 📚 Uso da Aplicação

### Interface Web (Angular)

1. **Acessar**: http://localhost:4200
2. **Criar Tarefa**:
   - Preencha o formulário no topo da página
   - Clique em "Criar Tarefa"
3. **Listar Tarefas**:
   - Todas as tarefas aparecem automaticamente
   - Use o filtro para ver apenas tarefas de um status específico
4. **Editar Tarefa**:
   - Clique em "Editar" no card da tarefa
   - Modifique os dados no formulário
   - Clique em "Atualizar Tarefa"
5. **Excluir Tarefa**:
   - Clique em "Excluir" no card da tarefa
   - Confirme a exclusão

### API REST (Postman/Insomnia)

#### Endpoints Disponíveis

##### 1. Criar Tarefa
```http
POST http://localhost:8080/api/tarefas
Content-Type: application/json

{
  "titulo": "Estudar Spring Boot",
  "descricao": "Revisar conceitos de injeção de dependência",
  "status": "PENDENTE"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "titulo": "Estudar Spring Boot",
  "descricao": "Revisar conceitos de injeção de dependência",
  "status": "PENDENTE",
  "dataCriacao": "2026-01-06T10:30:00",
  "dataAtualizacao": null
}
```

##### 2. Listar Todas as Tarefas
```http
GET http://localhost:8080/api/tarefas
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Estudar Spring Boot",
    "descricao": "Revisar conceitos de injeção de dependência",
    "status": "PENDENTE",
    "dataCriacao": "2026-01-06T10:30:00",
    "dataAtualizacao": null
  },
  {
    "id": 2,
    "titulo": "Implementar API REST",
    "descricao": "Criar endpoints CRUD",
    "status": "EM_ANDAMENTO",
    "dataCriacao": "2026-01-06T11:00:00",
    "dataAtualizacao": "2026-01-06T14:30:00"
  }
]
```

##### 3. Buscar Tarefa por ID
```http
GET http://localhost:8080/api/tarefas/1
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "titulo": "Estudar Spring Boot",
  "descricao": "Revisar conceitos de injeção de dependência",
  "status": "PENDENTE",
  "dataCriacao": "2026-01-06T10:30:00",
  "dataAtualizacao": null
}
```

##### 4. Listar Tarefas por Status
```http
GET http://localhost:8080/api/tarefas/status/PENDENTE
```

Status válidos: `PENDENTE`, `EM_ANDAMENTO`, `CONCLUIDA`

##### 5. Atualizar Tarefa
```http
PUT http://localhost:8080/api/tarefas/1
Content-Type: application/json

{
  "titulo": "Estudar Spring Boot - Avançado",
  "descricao": "Estudar Spring Security e JWT",
  "status": "EM_ANDAMENTO"
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "titulo": "Estudar Spring Boot - Avançado",
  "descricao": "Estudar Spring Security e JWT",
  "status": "EM_ANDAMENTO",
  "dataCriacao": "2026-01-06T10:30:00",
  "dataAtualizacao": "2026-01-06T15:45:00"
}
```

##### 6. Excluir Tarefa
```http
DELETE http://localhost:8080/api/tarefas/1
```

**Resposta (204 No Content)** - Sem corpo na resposta

#### Tratamento de Erros

##### Tarefa não encontrada (404)
```json
{
  "timestamp": "2026-01-06T15:45:00",
  "status": 404,
  "error": "Not Found",
  "message": "Tarefa não encontrada com ID: 99",
  "path": "/api/tarefas/99"
}
```

##### Erro de validação (400)
```json
{
  "timestamp": "2026-01-06T15:45:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Erro de validação nos dados da requisição",
  "path": "/api/tarefas",
  "errors": [
    "O título é obrigatório",
    "O título deve ter entre 3 e 100 caracteres"
  ]
}
```

---

## 📖 Documentação da API (Swagger)

A documentação interativa da API está disponível em:

**URL**: http://localhost:8080/swagger-ui.html

### Recursos do Swagger:
- 📋 Listagem completa de todos os endpoints
- 🧪 Interface para testar as requisições diretamente
- 📝 Descrição detalhada de cada parâmetro
- 📤 Exemplos de requisições e respostas
- 🔍 Schemas dos objetos de dados

---

## 🧪 Executando os Testes

### Testes Backend (JUnit)

```bash
cd backend

# Executar todos os testes
mvn test

# Executar com relatório de cobertura
mvn clean test jacoco:report

# Ver relatório: target/site/jacoco/index.html
```

### Testes Frontend (Jasmine/Karma)

```bash
cd frontend

# Executar testes
npm test

# Executar com cobertura
ng test --code-coverage

# Ver relatório: coverage/index.html
```

---

## 📊 Estrutura do Banco de Dados

### Tabela: `tarefas`

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| `id` | BIGINT (PK) | Identificador único |
| `titulo` | VARCHAR(100) | Título da tarefa |
| `descricao` | VARCHAR(500) | Descrição detalhada |
| `data_criacao` | DATETIME2 | Data de criação |
| `data_atualizacao` | DATETIME2 | Data da última atualização |
| `status` | VARCHAR(20) | Status (PENDENTE, EM_ANDAMENTO, CONCLUIDA) |

### Migrations (Flyway)

As migrations são executadas automaticamente na inicialização:

- `V1__criar_tabela_tarefas.sql` - Cria a estrutura do banco
- `V2__inserir_dados_iniciais.sql` - Insere dados de exemplo

---

## 🎯 Princípios e Boas Práticas Implementadas

### ✅ Clean Code
- Nomes descritivos e significativos
- Funções pequenas e focadas
- Comentários apenas quando necessário
- Código auto-explicativo
- Sem duplicação (DRY)

### ✅ Clean Architecture
- Separação de responsabilidades em camadas
- Dependências apontam para o domínio
- Regras de negócio independentes de frameworks
- Fácil manutenção e testabilidade

### ✅ SOLID
- **S**ingle Responsibility Principle
- **O**pen/Closed Principle
- **L**iskov Substitution Principle
- **I**nterface Segregation Principle
- **D**ependency Inversion Principle

### ✅ Outros Padrões
- RESTful API Design
- DTO Pattern
- Repository Pattern
- Service Layer Pattern
- Dependency Injection
- Exception Handling centralizado

---

## 📁 Estrutura Completa do Projeto

```
Stefanini-Desafio-t-cnico/
├── 📂 backend/
│   ├── 📂 src/
│   │   ├── 📂 main/
│   │   │   ├── 📂 java/com/stefanini/todolist/
│   │   │   │   ├── 📂 domain/
│   │   │   │   │   ├── 📂 entities/
│   │   │   │   │   │   └── Tarefa.java
│   │   │   │   │   ├── 📂 enums/
│   │   │   │   │   │   └── StatusTarefa.java
│   │   │   │   │   └── 📂 repositories/
│   │   │   │   │       └── TarefaRepository.java
│   │   │   │   ├── 📂 application/
│   │   │   │   │   ├── 📂 dtos/
│   │   │   │   │   │   ├── TarefaCreateDTO.java
│   │   │   │   │   │   ├── TarefaUpdateDTO.java
│   │   │   │   │   │   └── TarefaResponseDTO.java
│   │   │   │   │   └── 📂 services/
│   │   │   │   │       ├── TarefaService.java
│   │   │   │   │       └── TarefaServiceImpl.java
│   │   │   │   ├── 📂 infrastructure/
│   │   │   │   │   ├── 📂 controllers/
│   │   │   │   │   │   └── TarefaController.java
│   │   │   │   │   ├── 📂 config/
│   │   │   │   │   │   ├── OpenApiConfig.java
│   │   │   │   │   │   ├── ModelMapperConfig.java
│   │   │   │   │   │   └── CorsConfig.java
│   │   │   │   │   └── 📂 exceptions/
│   │   │   │   │       ├── ResourceNotFoundException.java
│   │   │   │   │       ├── ErrorResponse.java
│   │   │   │   │       └── GlobalExceptionHandler.java
│   │   │   │   └── TodoListApplication.java
│   │   │   └── 📂 resources/
│   │   │       ├── 📂 db/migration/
│   │   │       │   ├── V1__criar_tabela_tarefas.sql
│   │   │       │   └── V2__inserir_dados_iniciais.sql
│   │   │       ├── application.properties
│   │   │       └── application-test.properties
│   │   └── 📂 test/
│   │       └── 📂 java/com/stefanini/todolist/
│   │           └── 📂 services/
│   │               └── TarefaServiceImplTest.java
│   └── pom.xml
│
├── 📂 frontend/
│   ├── 📂 src/
│   │   ├── 📂 app/
│   │   │   ├── 📂 models/
│   │   │   │   └── tarefa.model.ts
│   │   │   ├── 📂 services/
│   │   │   │   └── tarefa.service.ts
│   │   │   ├── app.component.ts
│   │   │   ├── app.component.html
│   │   │   ├── app.component.scss
│   │   │   └── app.config.ts
│   │   ├── index.html
│   │   ├── main.ts
│   │   └── styles.scss
│   ├── angular.json
│   ├── package.json
│   └── tsconfig.json
│
└── README.md
```

---

## 🔄 GitFlow

Este projeto segue a estratégia de **GitFlow**:

### Branches Principais
- `main` - Código em produção
- `develop` - Branch de desenvolvimento

### Branches de Suporte
- `feature/*` - Novas funcionalidades
- `bugfix/*` - Correções de bugs
- `hotfix/*` - Correções urgentes em produção
- `release/*` - Preparação para release

### Exemplo de Workflow
```bash
# Criar feature
git checkout -b feature/nova-funcionalidade develop

# Desenvolver e commitar
git add .
git commit -m "feat: adiciona nova funcionalidade"

# Finalizar feature
git checkout develop
git merge feature/nova-funcionalidade
git branch -d feature/nova-funcionalidade
```

---

## 🚀 Deploy e CI/CD (Diferencial)

### Docker Compose

```yaml
# docker-compose.yml
version: '3.8'

services:
  sqlserver:
    image: mcr.microsoft.com/mssql/server:2019-latest
    environment:
      - ACCEPT_EULA=Y
      - SA_PASSWORD=SuaSenha@123
    ports:
      - "1433:1433"

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    depends_on:
      - sqlserver
    environment:
      - SPRING_DATASOURCE_URL=jdbc:sqlserver://sqlserver:1433;databaseName=todolist_db

  frontend:
    build: ./frontend
    ports:
      - "4200:80"
    depends_on:
      - backend
```

### GitHub Actions (CI/CD)

```yaml
# .github/workflows/ci-cd.yml
name: CI/CD Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main, develop]

jobs:
  backend-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Build and Test
        run: |
          cd backend
          mvn clean test

  frontend-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up Node.js
        uses: actions/setup-node@v2
        with:
          node-version: '18'
      - name: Install and Test
        run: |
          cd frontend
          npm install
          npm test
```

---

## 📝 Commits Semânticos

O projeto segue a convenção de **Conventional Commits**:

```
feat: adiciona nova funcionalidade
fix: corrige bug
docs: atualiza documentação
style: formatação de código
refactor: refatoração de código
test: adiciona testes
chore: tarefas de manutenção
```

---

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'feat: Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença **Apache 2.0**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

Desenvolvido como parte do **Desafio Técnico Stefanini**

---

## 📞 Suporte

Se encontrar algum problema ou tiver dúvidas:

1. Verifique a documentação acima
2. Consulte o Swagger: http://localhost:8080/swagger-ui.html
3. Abra uma issue no GitHub

---

## 🎓 Aprendizados

Este projeto demonstra conhecimento em:

- ✅ Desenvolvimento Full Stack (Java + Angular)
- ✅ API RESTful com Spring Boot
- ✅ Clean Architecture e SOLID
- ✅ Testes Unitários
- ✅ Documentação com Swagger
- ✅ Versionamento com Git/GitFlow
- ✅ Banco de dados SQL Server
- ✅ Frontend moderno com Angular 19
- ✅ Boas práticas de desenvolvimento

---

**Desenvolvido com ❤️ para o Desafio Técnico Stefanini**

