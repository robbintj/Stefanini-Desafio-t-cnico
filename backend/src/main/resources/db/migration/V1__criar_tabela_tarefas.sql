-- ===============================
-- MIGRATION: Criacao da tabela de tarefas
-- Descricao: Cria a estrutura inicial do banco de dados
-- Autor: Stefanini Challenge
-- Data: 2026-01-06
-- ===============================
-- Criacao da tabela de tarefas (compativel com H2)
CREATE TABLE tarefas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    -- Constraints
    CONSTRAINT chk_status CHECK (status IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA'))
);
-- Indices para otimizacao de consultas
CREATE INDEX idx_tarefas_status ON tarefas(status);
CREATE INDEX idx_tarefas_data_criacao ON tarefas(data_criacao);
