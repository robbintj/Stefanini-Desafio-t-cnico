# 🎨 Frontend - Sistema de Gerenciamento de Tarefas

Frontend moderno desenvolvido com **Angular 19**, **Bootstrap 5** e **TypeScript** para o desafio técnico Stefanini.

## 📋 Índice

- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Executando o Projeto](#executando-o-projeto)
- [Build para Produção](#build-para-produção)
- [Testes](#testes)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionalidades](#funcionalidades)
- [API Integration](#api-integration)
- [Troubleshooting](#troubleshooting)

---

## 🚀 Tecnologias Utilizadas

- **Angular 19.2** - Framework TypeScript
- **TypeScript 5.7** - Linguagem de programação
- **Bootstrap 5.3** - Framework CSS
- **Bootstrap Icons** - Biblioteca de ícones
- **RxJS** - Programação reativa
- **Angular Signals** - Gerenciamento de estado moderno
- **Vite** - Build tool rápido
- **SSR (Server-Side Rendering)** - Renderização do lado do servidor

---

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

### Node.js e npm
```bash
# Versão mínima requerida
node >= 18.19.0
npm >= 10.0.0

# Verificar versões instaladas
node -v
npm -v
```

### Angular CLI (opcional, mas recomendado)
```bash
# Instalar globalmente
npm install -g @angular/cli

# Verificar instalação
ng version
```

---

## 🔧 Instalação

### 1. Clone o repositório
```bash
git clone https://github.com/robbintj/Stefanini-Desafio-t-cnico.git
cd Stefanini-Desafio-t-cnico/frontend
```

### 2. Instale as dependências
```bash
npm install
```

### 3. Aguarde a instalação
O processo pode levar alguns minutos dependendo da sua conexão.

---

## ⚙️ Configuração

### 1. Configurar URL da API Backend

Edite o arquivo `src/app/services/tarefa.service.ts`:

```typescript
export class TarefaService {
  // Altere para a URL do seu backend
  private apiUrl = 'http://localhost:8080/api/tarefas';
}
```

---

## 🏃 Executando o Projeto

### Modo Desenvolvimento

```bash
# Inicia o servidor de desenvolvimento
ng serve

# Ou use npm
npm start
```

O aplicativo estará disponível em: **http://localhost:4200/**

#### Hot Module Replacement (HMR)
O HMR está habilitado por padrão. As alterações serão refletidas automaticamente no navegador.

### Executar em porta diferente

```bash
ng serve --port 4300
```

---

## 🏗️ Build para Produção

### Build básico
```bash
ng build

# Ou
npm run build
```

Os arquivos otimizados estarão em: `dist/frontend/browser/`

---

## �� Testes

### Testes Unitários
```bash
# Executar testes
ng test

# Ou
npm test
```

### Lint (Análise de Código)
```bash
ng lint
```

---

## 📁 Estrutura do Projeto

```
frontend/
├── .angular/               # Cache do Angular
├── node_modules/          # Dependências
├── public/                # Assets estáticos
├── src/                   # Código fonte
│   ├── app/              # Componentes da aplicação
│   │   ├── models/       # Interfaces e modelos
│   │   │   └── tarefa.model.ts
│   │   ├── services/     # Serviços (API, etc)
│   │   │   └── tarefa.service.ts
│   │   ├── app.component.ts
│   │   ├── app.component.html
│   │   ├── app.component.scss
│   │   ├── app.config.ts
│   │   └── app.routes.ts
│   ├── index.html        # HTML principal
│   ├── main.ts           # Entry point
│   ├── styles.scss       # Estilos globais
│   └── server.ts         # Configuração SSR
├── angular.json          # Configuração Angular
├── package.json          # Dependências e scripts
├── tsconfig.json         # Configuração TypeScript
└── README.md            # Este arquivo
```

---

## ✨ Funcionalidades

### 📝 Gerenciamento de Tarefas
- ✅ **Criar** novas tarefas
- ✏️ **Editar** tarefas existentes
- 🗑️ **Excluir** tarefas
- 📋 **Listar** todas as tarefas
- 🔍 **Filtrar** por status (Pendente, Em Andamento, Concluída)

### 🎨 Interface Moderna
- Design responsivo com Bootstrap 5
- Gradientes e animações suaves
- Toasts para feedback do usuário
- Cards interativos com hover effects
- Ícones Bootstrap Icons
- Layout adaptável mobile-first

### 🔄 Funcionalidades Técnicas
- **Signals** do Angular para gerenciamento de estado reativo
- **Standalone Components** (sem NgModules)
- **Nova sintaxe de Control Flow** (`@if`, `@for`)
- **Two-way data binding** com `[(ngModel)]`
- **HTTP Client** para comunicação com API REST
- **RxJS Observables** para programação reativa
- **TypeScript** strict mode
- **SSR** para melhor SEO e performance

---

## 🔌 API Integration

### Endpoints Consumidos

```typescript
// GET - Listar todas as tarefas
GET http://localhost:8080/api/tarefas

// GET - Listar por status
GET http://localhost:8080/api/tarefas/status/{status}

// GET - Buscar por ID
GET http://localhost:8080/api/tarefas/{id}

// POST - Criar tarefa
POST http://localhost:8080/api/tarefas

// PUT - Atualizar tarefa
PUT http://localhost:8080/api/tarefas/{id}

// DELETE - Excluir tarefa
DELETE http://localhost:8080/api/tarefas/{id}
```

### Modelo de Dados

```typescript
interface Tarefa {
  id?: number;
  titulo: string;
  descricao: string;
  status: StatusTarefa;
  dataCriacao?: string;
  dataAtualizacao?: string;
}

enum StatusTarefa {
  PENDENTE = 'PENDENTE',
  EM_ANDAMENTO = 'EM_ANDAMENTO',
  CONCLUIDA = 'CONCLUIDA'
}
```

---

## 🐛 Troubleshooting

### Erro: Port 4200 já está em uso

```bash
# Matar processo na porta 4200
lsof -ti:4200 | xargs kill -9

# Ou usar outra porta
ng serve --port 4300
```

### Erro: Module not found

```bash
# Limpar node_modules e reinstalar
rm -rf node_modules package-lock.json
npm install
```

### Erro: Cache do Angular corrompido

```bash
# Limpar cache
rm -rf .angular/cache
rm -rf node_modules/.vite

# Reiniciar servidor
ng serve
```

### Erro: CORS ao conectar com backend

Certifique-se de que o backend tem CORS configurado para permitir `http://localhost:4200`

---

## 📝 Scripts Disponíveis

```bash
npm start              # Servidor de desenvolvimento
npm run build         # Build de produção
npm test              # Testes unitários
npm run lint          # Análise de código
```

---

## 🎯 Melhorias Futuras

- [ ] Implementar testes E2E
- [ ] Adicionar internacionalização (i18n)
- [ ] Implementar lazy loading de rotas
- [ ] Adicionar PWA capabilities
- [ ] Implementar autenticação/autorização
- [ ] Adicionar modo escuro (dark mode)

---

## 📄 Licença

Este projeto foi desenvolvido como parte do desafio técnico Stefanini.

---

## 🔗 Links Úteis

- [Documentação Angular](https://angular.dev)
- [Bootstrap Documentation](https://getbootstrap.com)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [Angular Signals Guide](https://angular.dev/guide/signals)

---

**🚀 Happy Coding!**
