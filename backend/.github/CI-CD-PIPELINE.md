# Pipeline CI/CD - Stefanini Todo List

## 📋 Visão Geral

Este pipeline automatiza o processo de build, testes e análise de código para o projeto Todo List, garantindo a qualidade e integridade do código antes da integração.

## 🔧 Configuração

### Requisitos
- **Backend:** Java 21 (Temurin Distribution)
- **Frontend:** Node.js 20
- **Build Tool Backend:** Maven
- **Build Tool Frontend:** npm

### Triggers
O pipeline é executado automaticamente em:
- ✅ Push nas branches `main` e `develop`
- ✅ Pull Requests para as branches `main` e `develop`

## 🚀 Jobs do Pipeline

### 1️⃣ Backend Build and Test

**Nome:** `backend-build-and-test`

**Etapas:**
1. **Checkout código** - Clona o repositório
2. **Configurar JDK 21** - Instala Java 21 (Temurin) com cache Maven
3. **Build com Maven** - Compila o projeto (`mvn clean install -DskipTests`)
4. **Executar testes** - Roda todos os testes unitários (`mvn test`)
5. **Gerar relatório de cobertura** - Cria relatório JaCoCo (`mvn jacoco:report`)
6. **Upload do relatório** - Salva o relatório como artefato

**Artefatos gerados:**
- `coverage-report` - Relatório de cobertura de código JaCoCo

### 2️⃣ Frontend Build and Test

**Nome:** `frontend-build-and-test`

**Etapas:**
1. **Checkout código** - Clona o repositório
2. **Configurar Node.js** - Instala Node.js 20 com cache npm
3. **Instalar dependências** - Instala pacotes (`npm ci`)
4. **Lint** - Executa verificação de código (continua mesmo com erros)
5. **Build** - Compila o frontend (`npm run build`)
6. **Executar testes** - Roda testes com Chrome Headless

### 3️⃣ Code Quality

**Nome:** `code-quality`

**Dependências:** Aguarda conclusão dos jobs de backend e frontend

**Etapas:**
1. **Checkout código** - Clona o repositório
2. **Análise de segurança** - Placeholder para ferramentas como SonarQube, Snyk

**Extensões sugeridas:**
- SonarQube para análise de código
- Snyk para scan de vulnerabilidades
- OWASP Dependency Check
- SpotBugs para análise estática

### 4️⃣ Docker Build

**Nome:** `docker-build`

**Dependências:** Aguarda conclusão dos jobs de backend e frontend

**Condição:** Executa apenas em push para a branch `main`

**Etapas:**
1. **Checkout código** - Clona o repositório
2. **Informar build Docker** - Placeholder para build de imagens

**Imagens planejadas:**
- `stefanini/todolist-backend:latest`
- `stefanini/todolist-frontend:latest`

## 📊 Fluxo de Execução

```
┌─────────────────────────────────────────┐
│   Push/PR → main ou develop             │
└──────────────┬──────────────────────────┘
               │
               ├──────────────┬──────────────┐
               │              │              │
               ▼              ▼              │
    ┌──────────────────┐  ┌──────────────┐  │
    │ Backend Build    │  │ Frontend     │  │
    │ & Test           │  │ Build & Test │  │
    └────────┬─────────┘  └──────┬───────┘  │
             │                   │           │
             └──────┬────────────┘           │
                    ▼                        │
          ┌──────────────────┐               │
          │  Code Quality    │               │
          └────────┬─────────┘               │
                   │                         │
                   └────────┬────────────────┘
                            ▼
                   ┌──────────────────┐
                   │  Docker Build    │ (apenas main)
                   └──────────────────┘
```

## 🔍 Verificações Realizadas

### Backend (Java 21)
- ✅ Compilação do código
- ✅ Execução de testes unitários
- ✅ Cobertura de código (JaCoCo)
- ✅ Validação de dependências Maven

### Frontend (Node.js 20)
- ✅ Instalação de dependências
- ✅ Lint de código
- ✅ Build de produção
- ✅ Testes unitários

## 📈 Cobertura de Código

O relatório de cobertura JaCoCo é gerado automaticamente e disponibilizado como artefato do workflow. Para visualizar:

1. Acesse a aba "Actions" no GitHub
2. Selecione o workflow executado
3. Baixe o artefato `coverage-report`
4. Abra o arquivo `index.html` em um navegador

## 🔐 Boas Práticas Implementadas

### Segurança
- ✅ Usa versões específicas das actions (@v4)
- ✅ Cache de dependências para otimização
- ✅ Execução em ambiente isolado (Ubuntu latest)

### Performance
- ✅ Cache Maven para backend
- ✅ Cache npm para frontend
- ✅ Execução paralela de jobs independentes
- ✅ Skip de testes no build inicial (maven install)

### Qualidade
- ✅ Testes obrigatórios antes do merge
- ✅ Relatórios de cobertura
- ✅ Análise de código planejada
- ✅ Docker build apenas para branch principal

## 🚀 Próximos Passos

### Melhorias Sugeridas

1. **Integração com SonarQube**
   ```yaml
   - name: SonarQube Scan
     uses: sonarsource/sonarqube-scan-action@v2
     with:
       projectKey: stefanini-todolist
   ```

2. **Scan de Vulnerabilidades**
   ```yaml
   - name: Snyk Security Scan
     uses: snyk/actions/maven@master
     with:
       args: --severity-threshold=high
   ```

3. **Build e Push de Imagens Docker**
   ```yaml
   - name: Build and push Docker images
     uses: docker/build-push-action@v5
     with:
       context: ./backend
       push: true
       tags: stefanini/todolist-backend:${{ github.sha }}
   ```

4. **Deploy Automatizado**
   ```yaml
   - name: Deploy to Production
     if: github.ref == 'refs/heads/main'
     run: |
       # Script de deploy
   ```

5. **Notificações**
   ```yaml
   - name: Slack Notification
     uses: 8398a7/action-slack@v3
     if: always()
     with:
       status: ${{ job.status }}
   ```

## 📝 Configuração Local

Para rodar os mesmos comandos localmente:

### Backend
```bash
cd backend
mvn clean install
mvn test
mvn jacoco:report
```

### Frontend
```bash
cd frontend
npm ci
npm run lint
npm run build
npm test
```

## 🆘 Troubleshooting

### Falha no Build do Backend
- Verifique a versão do Java (deve ser 21)
- Execute `mvn clean` antes de `mvn install`
- Verifique logs do Maven no workflow

### Falha no Build do Frontend
- Verifique a versão do Node.js (deve ser 20)
- Delete `node_modules` e execute `npm ci` novamente
- Verifique o arquivo `package-lock.json`

### Cache não está funcionando
- Limpe o cache nas configurações do repositório
- Verifique se o path do cache está correto
- Aguarde a primeira execução completa

## 📚 Referências

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven CI/CD](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
- [JaCoCo Maven Plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
- [Docker Build Push Action](https://github.com/docker/build-push-action)

## ✅ Status

- ✅ Pipeline configurado para Java 21
- ✅ Versões atualizadas das GitHub Actions (v4)
- ✅ Node.js atualizado para versão 20
- ✅ Cache implementado para Maven e npm
- ✅ Fluxo paralelo otimizado
- ✅ Pronto para extensões futuras

---

**Criado em:** 07 de Janeiro de 2026  
**Versão:** 1.0.0  
**Projeto:** Stefanini Todo List

