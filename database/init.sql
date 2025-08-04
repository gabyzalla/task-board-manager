-- Script de inicialização do banco de dados TaskBoard
-- Execute este script no MySQL para criar o banco de dados

-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS taskboard
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Usar o banco de dados
USE taskboard;

-- As tabelas serão criadas automaticamente pelo sistema
-- Este script apenas prepara o banco de dados

-- Verificar se o banco foi criado
SELECT 'Banco de dados taskboard criado com sucesso!' AS Status; 