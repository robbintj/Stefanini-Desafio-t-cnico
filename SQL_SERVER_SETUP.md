# Scripts SQL - Guia de Instalação do SQL Server

## 🗄️ Instalação do SQL Server

> **🍎 RECOMENDADO PARA macOS (incluindo Sonoma 14.5):** Use a **Opção 2 (Docker)** - É a solução oficial da Microsoft para macOS, pois SQL Server não tem versão nativa para Mac.

---

## 🍎 PARA macOS SONOMA 14.5 (RECOMENDADO)

### Passo 1: Instalar Docker Desktop

1. **Download Docker Desktop para Mac:**
   - Acesse: https://www.docker.com/products/docker-desktop
   - Clique em "Download for Mac"
   - Escolha a versão correta:
     - **Apple Silicon (M1/M2/M3)**: Download Mac with Apple chip
     - **Intel**: Download Mac with Intel chip

2. **Instalar:**
   ```bash
   # Abra o arquivo .dmg baixado
   # Arraste Docker para Applications
   # Abra Docker Desktop
   # Aguarde inicializar (ícone aparece na barra superior)
   ```

3. **Verificar instalação:**
   ```bash
   docker --version
   # Deve mostrar: Docker version 24.x.x ou superior
   ```

### Passo 2: Executar SQL Server no Docker

```bash
# No Terminal do macOS, execute:

docker run -e "ACCEPT_EULA=Y" \
  -e "SA_PASSWORD=SuaSenha@123" \
  -p 1433:1433 \
  --name sqlserver \
  --hostname sqlserver \
  -d mcr.microsoft.com/mssql/server:2019-latest

# Aguardar ~30 segundos para o SQL Server iniciar

# Verificar se está rodando
docker ps

# Ver logs (opcional)
docker logs sqlserver
```

### Passo 3: Instalar Azure Data Studio (Interface Gráfica - Opcional)

```bash
# Opção 1: Via Homebrew (recomendado)
brew install --cask azure-data-studio

# Opção 2: Download manual
# Acesse: https://docs.microsoft.com/sql/azure-data-studio/download
# Baixe versão para macOS
```

### Passo 4: Conectar ao SQL Server

**Via Azure Data Studio:**
- Server: `localhost,1433` ou `localhost`
- Authentication type: `SQL Login`
- User name: `sa`
- Password: `SuaSenha@123`

**Via Terminal (sqlcmd no container):**
```bash
docker exec -it sqlserver /opt/mssql-tools/bin/sqlcmd \
  -S localhost -U sa -P 'SuaSenha@123'
```

### Comandos Úteis para macOS

```bash
# Parar SQL Server
docker stop sqlserver

# Iniciar SQL Server
docker start sqlserver

# Reiniciar SQL Server
docker restart sqlserver

# Ver status
docker ps -a | grep sqlserver

# Remover container (cuidado: perde dados)
docker rm -f sqlserver

# Ver logs em tempo real
docker logs -f sqlserver
```

---

## 💻 Opção 1: Windows - SQL Server Express

> **Nota:** Esta opção é apenas para Windows. Se você está no macOS, use a opção Docker acima.

1. **Download:**
   - Acesse: https://www.microsoft.com/sql-server/sql-server-downloads
   - Clique em "Download now" na versão Express (gratuita)

2. **Instalação:**
   ```
   - Execute o instalador baixado
   - Escolha "Basic" ou "Custom"
   - Aceite os termos de licença
   - Escolha o diretório de instalação
   - Aguarde a instalação
   ```

3. **Configuração após instalação:**
   - Anote a string de conexão fornecida
   - Instale o SQL Server Management Studio (SSMS) se desejar interface gráfica

---

## 🐳 Opção 2: Docker (Multiplataforma - RECOMENDADO PARA macOS)

```bash
# Pull da imagem
docker pull mcr.microsoft.com/mssql/server:2019-latest

# Executar container
docker run -e "ACCEPT_EULA=Y" \
  -e "SA_PASSWORD=SuaSenha@123" \
  -p 1433:1433 \
  --name sqlserver \
  --hostname sqlserver \
  -d mcr.microsoft.com/mssql/server:2019-latest

# Verificar se está rodando
docker ps

# Ver logs
docker logs sqlserver
```


---

## 🔧 Scripts de Configuração Manual

### 1. Criar Banco de Dados

Execute este script no SQL Server Management Studio ou Azure Data Studio:

```sql
-- ==============================================
-- Script: Criação do Banco de Dados
-- ==============================================

-- Criar banco de dados
CREATE DATABASE todolist_db;
GO

-- Usar o banco de dados
USE todolist_db;
GO

-- Verificar se foi criado
SELECT name, database_id, create_date 
FROM sys.databases 
WHERE name = 'todolist_db';
GO
```

### 2. Criar Tabela de Tarefas

```sql
-- ==============================================
-- Script: Criação da Tabela de Tarefas
-- ==============================================

USE todolist_db;
GO

-- Criar tabela
CREATE TABLE tarefas (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    data_criacao DATETIME2 NOT NULL DEFAULT GETDATE(),
    data_atualizacao DATETIME2,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    
    -- Constraints
    CONSTRAINT chk_status CHECK (status IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA'))
);
GO

-- Criar índices
CREATE INDEX idx_tarefas_status ON tarefas(status);
CREATE INDEX idx_tarefas_data_criacao ON tarefas(data_criacao);
GO

-- Verificar estrutura
SELECT 
    c.name AS column_name,
    t.name AS data_type,
    c.max_length,
    c.is_nullable
FROM sys.columns c
INNER JOIN sys.types t ON c.user_type_id = t.user_type_id
WHERE c.object_id = OBJECT_ID('tarefas');
GO
```

### 3. Inserir Dados de Teste

```sql
-- ==============================================
-- Script: Dados Iniciais para Testes
-- ==============================================

USE todolist_db;
GO

-- Inserir tarefas de exemplo
INSERT INTO tarefas (titulo, descricao, status) VALUES
('Estudar Spring Boot', 'Revisar conceitos de injeção de dependência e configuração', 'PENDENTE'),
('Implementar API REST', 'Criar endpoints CRUD seguindo padrões RESTful', 'EM_ANDAMENTO'),
('Configurar SQL Server', 'Instalar e configurar o banco de dados localmente', 'CONCLUIDA'),
('Documentar com Swagger', 'Adicionar anotações para documentação automática da API', 'PENDENTE'),
('Escrever testes unitários', 'Criar testes para controllers e services', 'PENDENTE'),
('Configurar CORS', 'Permitir requisições do frontend Angular', 'CONCLUIDA'),
('Criar componentes Angular', 'Desenvolver interface de usuário com Angular 19', 'EM_ANDAMENTO'),
('Implementar validações', 'Adicionar validações nos DTOs e no frontend', 'CONCLUIDA');
GO

-- Verificar dados inseridos
SELECT * FROM tarefas ORDER BY data_criacao DESC;
GO

-- Estatísticas por status
SELECT 
    status,
    COUNT(*) as total
FROM tarefas
GROUP BY status
ORDER BY status;
GO
```

---

## 🔍 Scripts de Consulta Úteis

### Listar todas as tarefas
```sql
SELECT * FROM tarefas ORDER BY data_criacao DESC;
```

### Buscar por status
```sql
SELECT * FROM tarefas WHERE status = 'PENDENTE';
```

### Contar tarefas por status
```sql
SELECT 
    status,
    COUNT(*) as quantidade
FROM tarefas
GROUP BY status;
```

### Buscar por título
```sql
SELECT * FROM tarefas 
WHERE titulo LIKE '%Spring%'
ORDER BY data_criacao DESC;
```

### Tarefas criadas hoje
```sql
SELECT * FROM tarefas
WHERE CAST(data_criacao AS DATE) = CAST(GETDATE() AS DATE);
```

### Tarefas atualizadas
```sql
SELECT * FROM tarefas
WHERE data_atualizacao IS NOT NULL
ORDER BY data_atualizacao DESC;
```

---

## 🧹 Scripts de Limpeza

### Limpar todos os dados
```sql
TRUNCATE TABLE tarefas;
```

### Deletar tabela
```sql
DROP TABLE IF EXISTS tarefas;
```

### Deletar banco de dados
```sql
USE master;
GO

DROP DATABASE IF EXISTS todolist_db;
GO
```

---

## 🔐 Configuração de Segurança

### Criar usuário específico (recomendado)
```sql
USE master;
GO

-- Criar login
CREATE LOGIN todolist_user WITH PASSWORD = 'Senha@Segura123';
GO

-- Usar banco de dados
USE todolist_db;
GO

-- Criar usuário
CREATE USER todolist_user FOR LOGIN todolist_user;
GO

-- Conceder permissões
GRANT SELECT, INSERT, UPDATE, DELETE ON tarefas TO todolist_user;
GO
```

### String de conexão com usuário específico
```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=todolist_db
spring.datasource.username=todolist_user
spring.datasource.password=Senha@Segura123
```

---

## 🧪 Teste de Conexão

### Via SQL
```sql
SELECT 
    @@VERSION as versao_sql_server,
    @@SERVERNAME as nome_servidor,
    DB_NAME() as banco_atual,
    SYSTEM_USER as usuario_atual,
    GETDATE() as data_hora_servidor;
```

### Via Java (código de teste)
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=todolist_db;encrypt=true;trustServerCertificate=true";
        String usuario = "sa";
        String senha = "SuaSenha@123";
        
        try (Connection conn = DriverManager.getConnection(url, usuario, senha)) {
            System.out.println("✅ Conexão bem-sucedida!");
            System.out.println("Banco: " + conn.getCatalog());
        } catch (SQLException e) {
            System.out.println("❌ Erro na conexão: " + e.getMessage());
        }
    }
}
```

---

## 📊 Backup e Restore

### Fazer backup
```sql
BACKUP DATABASE todolist_db
TO DISK = 'C:\Backups\todolist_db.bak'
WITH FORMAT, INIT, NAME = 'Full Backup of todolist_db';
GO
```

### Restaurar backup
```sql
USE master;
GO

RESTORE DATABASE todolist_db
FROM DISK = 'C:\Backups\todolist_db.bak'
WITH REPLACE;
GO
```

---

## ⚠️ Troubleshooting

### Erro: Login failed for user 'sa'
```sql
-- Habilitar autenticação SQL Server
USE master;
GO

ALTER LOGIN sa ENABLE;
ALTER LOGIN sa WITH PASSWORD = 'SuaSenha@123';
GO

-- Reiniciar serviço SQL Server
```

### Erro: Cannot open database
```sql
-- Verificar se o banco existe
SELECT name FROM sys.databases;
GO

-- Verificar permissões
SELECT * FROM sys.database_permissions;
GO
```

### Porta 1433 não acessível
```bash
# Windows: Verificar firewall
netsh advfirewall firewall add rule name="SQL Server" dir=in action=allow protocol=TCP localport=1433

# Verificar se serviço está rodando
sc query MSSQLSERVER
```

---

## 📝 Notas Importantes

1. **Senha forte:** Use sempre senhas fortes em produção
2. **Backup regular:** Configure backups automáticos
3. **Monitoring:** Monitore o uso de recursos do banco
4. **Índices:** Os índices já estão criados para otimização
5. **Flyway:** Em produção, deixe o Flyway gerenciar as migrations

---

**Desenvolvido para o Desafio Técnico Stefanini**

