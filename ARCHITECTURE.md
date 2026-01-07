# 🏛️ Arquitetura do Sistema

## 📊 Visão Geral

O sistema de gerenciamento de tarefas foi desenvolvido seguindo os princípios de **Clean Architecture**, garantindo separação de responsabilidades, manutenibilidade e testabilidade.

## 🎯 Princípios Aplicados

### Clean Architecture

A aplicação está organizada em 3 camadas principais:

```
┌─────────────────────────────────────────┐
│         Infrastructure Layer            │
│  (Controllers, Config, Exceptions)      │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│         Application Layer               │
│    (Services, DTOs, Use Cases)          │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│           Domain Layer                  │
│  (Entities, Enums, Repositories)        │
└─────────────────────────────────────────┘
```

#### Domain Layer (Núcleo)
- **Responsabilidade**: Regras de negócio e entidades de domínio
- **Independente de**: Frameworks, banco de dados, UI
- **Contém**:
  - `Tarefa.java` - Entidade principal
  - `StatusTarefa.java` - Enum de status
  - `TarefaRepository.java` - Interface de persistência

#### Application Layer (Casos de Uso)
- **Responsabilidade**: Orquestração de lógica de negócio
- **Depende de**: Domain Layer
- **Contém**:
  - `TarefaService.java` - Interface de serviço
  - `TarefaServiceImpl.java` - Implementação da lógica
  - DTOs para transferência de dados

#### Infrastructure Layer (Frameworks)
- **Responsabilidade**: Detalhes de implementação
- **Depende de**: Application e Domain Layers
- **Contém**:
  - Controllers REST
  - Configurações Spring
  - Exception Handlers

### SOLID Principles

#### 1. Single Responsibility Principle (SRP)
Cada classe tem uma única responsabilidade:

```java
// ✅ BOM: Responsabilidade única
@Service
public class TarefaServiceImpl {
    // Apenas lógica de negócio de tarefas
}

@RestController
public class TarefaController {
    // Apenas exposição de endpoints REST
}

@Repository
public interface TarefaRepository {
    // Apenas operações de persistência
}
```

#### 2. Open/Closed Principle (OCP)
Aberto para extensão, fechado para modificação:

```java
// Interface para extensão
public interface TarefaService {
    TarefaResponseDTO criar(TarefaCreateDTO dto);
    // ... outros métodos
}

// Implementação pode ser estendida sem modificar a interface
@Service
public class TarefaServiceImpl implements TarefaService {
    @Override
    public TarefaResponseDTO criar(TarefaCreateDTO dto) {
        // Implementação
    }
}
```

#### 3. Liskov Substitution Principle (LSP)
Implementações podem substituir suas abstrações:

```java
// Qualquer implementação de TarefaService pode ser usada
private final TarefaService tarefaService;

// Funciona com TarefaServiceImpl ou qualquer outra implementação
```

#### 4. Interface Segregation Principle (ISP)
Interfaces específicas ao invés de genéricas:

```java
// ✅ Interface específica para Tarefas
public interface TarefaService {
    // Métodos específicos de tarefas
}

// Não usamos uma interface genérica com dezenas de métodos
```

#### 5. Dependency Inversion Principle (DIP)
Depender de abstrações, não de concreções:

```java
@RestController
public class TarefaController {
    // Depende da interface, não da implementação
    private final TarefaService tarefaService;
    
    @Autowired
    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }
}
```

## 🔄 Fluxo de Dados

### Fluxo de Criação de Tarefa

```
Cliente (Angular)
    │
    │ HTTP POST /api/tarefas
    ▼
TarefaController
    │
    │ TarefaCreateDTO
    ▼
TarefaService
    │
    │ Valida e transforma
    ▼
ModelMapper (DTO → Entity)
    │
    ▼
TarefaRepository
    │
    │ JPA/Hibernate
    ▼
SQL Server Database
    │
    │ Tarefa salva
    ▼
TarefaRepository
    │
    ▼
ModelMapper (Entity → DTO)
    │
    ▼
TarefaController
    │
    │ TarefaResponseDTO
    ▼
Cliente (Angular)
```

## 🎨 Padrões de Design Utilizados

### 1. Repository Pattern
```java
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByStatus(StatusTarefa status);
}
```
**Benefícios**: Abstração da camada de persistência

### 2. Service Layer Pattern
```java
@Service
public class TarefaServiceImpl implements TarefaService {
    // Lógica de negócio centralizada
}
```
**Benefícios**: Separação de responsabilidades

### 3. DTO Pattern
```java
public class TarefaCreateDTO {
    private String titulo;
    private String descricao;
    private StatusTarefa status;
}
```
**Benefícios**: Controle sobre dados expostos

### 4. Dependency Injection
```java
@RequiredArgsConstructor // Lombok
public class TarefaServiceImpl {
    private final TarefaRepository repository;
    private final ModelMapper mapper;
}
```
**Benefícios**: Baixo acoplamento e testabilidade

### 5. Builder Pattern
```java
Tarefa tarefa = Tarefa.builder()
    .titulo("Título")
    .descricao("Descrição")
    .status(StatusTarefa.PENDENTE)
    .build();
```
**Benefícios**: Criação fluente de objetos

## 📦 Estrutura de Pacotes

### Backend
```
com.stefanini.todolist
├── domain
│   ├── entities        # Entidades JPA
│   ├── enums          # Enumerações
│   └── repositories   # Interfaces de repositório
├── application
│   ├── dtos           # Data Transfer Objects
│   └── services       # Lógica de negócio
└── infrastructure
    ├── controllers    # REST Controllers
    ├── config         # Configurações Spring
    └── exceptions     # Tratamento de exceções
```

### Frontend
```
src/app
├── models             # Interfaces TypeScript
├── services           # Serviços HTTP
└── components         # Componentes Angular
```

## 🔐 Segurança

### Validações no Backend
```java
@NotBlank(message = "O título é obrigatório")
@Size(min = 3, max = 100)
private String titulo;
```

### CORS Configurado
```java
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        // Permite requisições do Angular
    }
}
```

### Exception Handling Centralizado
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound() {
        // Tratamento padronizado
    }
}
```

## 📊 Banco de Dados

### Modelo de Dados
```sql
CREATE TABLE tarefas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    data_criacao DATETIME2 NOT NULL,
    data_atualizacao DATETIME2,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT chk_status CHECK (status IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA'))
);
```

### Migrations com Flyway
- Versionamento automático do banco
- Scripts SQL em `db/migration`
- Execução automática na inicialização

## 🧪 Testabilidade

### Testes Unitários
```java
@ExtendWith(MockitoExtension.class)
class TarefaServiceImplTest {
    @Mock
    private TarefaRepository repository;
    
    @InjectMocks
    private TarefaServiceImpl service;
    
    @Test
    void deveCriarTarefa() {
        // Teste isolado
    }
}
```

### Separação de Concerns
- Domain independente de frameworks
- Services testáveis com mocks
- Controllers testáveis com MockMvc

## 📈 Escalabilidade

### Stateless Architecture
- API REST sem estado
- Permite escalonamento horizontal
- Load balancing facilitado

### Caching (Futuro)
```java
@Cacheable("tarefas")
public List<Tarefa> findAll() {
    // Cache automático com Spring Cache
}
```

### Async Processing (Futuro)
```java
@Async
public CompletableFuture<Void> processarTarefa() {
    // Processamento assíncrono
}
```

## 🚀 Deploy

### Containerização
- Docker para backend e frontend
- Docker Compose para orquestração
- Imagens otimizadas multi-stage

### CI/CD
- GitHub Actions
- Testes automatizados
- Deploy automatizado (opcional)

## 📚 Boas Práticas Implementadas

✅ **Clean Code**
- Nomes descritivos
- Métodos pequenos e focados
- Comentários quando necessário

✅ **RESTful API**
- URLs semânticas
- Verbos HTTP corretos
- Status codes apropriados

✅ **Versionamento**
- GitFlow
- Commits semânticos
- Branches organizadas

✅ **Documentação**
- JavaDoc completo
- Swagger/OpenAPI
- README detalhado

✅ **Logging**
- Logs estruturados
- Níveis apropriados
- Informações relevantes

## 🎯 Decisões Arquiteturais

### Por que Clean Architecture?
- Facilita testes
- Código desacoplado
- Manutenção simplificada
- Regras de negócio isoladas

### Por que SQL Server?
- Requisito do desafio
- Robustez empresarial
- Suporte a transações complexas

### Por que Angular Standalone?
- Arquitetura moderna
- Menos boilerplate
- Melhor performance
- Tree-shaking otimizado

### Por que Spring Boot?
- Produtividade
- Ecossistema maduro
- Convenção sobre configuração
- Comunidade ativa

## 📊 Métricas de Qualidade

- **Cobertura de Testes**: Objetivo >80%
- **Complexidade Ciclomática**: Mantida baixa
- **Code Smells**: Zero tolerância
- **Duplicação**: Minimizada com DRY

---

**Esta arquitetura foi projetada pensando em manutenibilidade, testabilidade e escalabilidade, seguindo as melhores práticas da indústria.**

