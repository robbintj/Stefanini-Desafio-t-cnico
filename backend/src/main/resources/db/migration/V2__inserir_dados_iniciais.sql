-- ===============================
-- MIGRATION: Dados iniciais para testes
-- Descricao: Insere tarefas de exemplo no banco de dados
-- Autor: Stefanini Challenge
-- Data: 2026-01-06
-- ===============================

-- Inserindo tarefas de exemplo (compativel com H2 e SQL Server)
INSERT INTO tarefas (titulo, descricao, data_criacao, status) VALUES
('Estudar Spring Boot', 'Revisar conceitos de injecao de dependencia e configuracao', CURRENT_TIMESTAMP, 'PENDENTE'),
('Implementar API REST', 'Criar endpoints CRUD seguindo padroes RESTful', CURRENT_TIMESTAMP, 'EM_ANDAMENTO'),
('Configurar SQL Server', 'Instalar e configurar o banco de dados localmente', CURRENT_TIMESTAMP, 'CONCLUIDA'),
('Documentar com Swagger', 'Adicionar anotacoes para documentacao automatica da API', CURRENT_TIMESTAMP, 'PENDENTE'),
('Escrever testes unitarios', 'Criar testes para controllers e services', CURRENT_TIMESTAMP, 'PENDENTE');

