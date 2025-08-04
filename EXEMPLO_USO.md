# Exemplo de Uso - Sistema de Gerenciamento de Tarefas

Este guia demonstra como usar o sistema através de um exemplo prático de gerenciamento de um projeto de desenvolvimento.

## Cenário: Projeto de Desenvolvimento Web

Vamos criar um board para gerenciar um projeto de desenvolvimento web com as seguintes etapas:
- Análise de Requisitos
- Desenvolvimento
- Testes
- Deploy

## Passo 1: Criar o Board

```
Sistema de Gerenciamento de Tarefas
===================================

Menu Principal:
1 - Criar novo board
2 - Selecionar board
3 - Excluir boards
4 - Sair

Escolha uma opção: 1

Criar Novo Board
================
Digite o nome do board: Projeto Web E-commerce

Board 'Projeto Web E-commerce' criado com sucesso!
Colunas criadas:
- A Fazer (INITIAL)
- Em Andamento (PENDING)
- Concluído (FINAL)
- Cancelado (CANCELLATION)
```

## Passo 2: Selecionar o Board

```
Menu Principal:
1 - Criar novo board
2 - Selecionar board
3 - Excluir boards
4 - Sair

Escolha uma opção: 2

Boards Disponíveis:
===================
1 - Projeto Web E-commerce (4 colunas)

Escolha o board (0 para voltar): 1
```

## Passo 3: Visualizar o Board

```
Board: Projeto Web E-commerce
================================

Estado Atual do Board:
======================

A Fazer (INITIAL):
------------------
  (vazio)

Em Andamento (PENDING):
-----------------------
  (vazio)

Concluído (FINAL):
------------------
  (vazio)

Cancelado (CANCELLATION):
-------------------------
  (vazio)

Menu do Board:
1 - Mover card para próxima coluna
2 - Cancelar card
3 - Criar card
4 - Bloquear card
5 - Desbloquear card
6 - Gerar relatório de conclusão
7 - Gerar relatório de bloqueios
8 - Voltar ao menu principal
```

## Passo 4: Criar Cards

Vamos criar algumas tarefas para o projeto:

### Criar Card 1: Análise de Requisitos
```
Escolha uma opção: 3

Criar Novo Card
===============
Título do card: Análise de Requisitos do Sistema
Descrição do card: Realizar levantamento completo dos requisitos funcionais e não funcionais

Colunas disponíveis:
1 - A Fazer (INITIAL)
2 - Em Andamento (PENDING)
3 - Concluído (FINAL)
4 - Cancelado (CANCELLATION)

Digite o ID da coluna para criar o card: 1

Card criado com sucesso! ID: 1
```

### Criar Card 2: Desenvolvimento Frontend
```
Escolha uma opção: 3

Criar Novo Card
===============
Título do card: Desenvolvimento Frontend
Descrição do card: Implementar interface do usuário com React

Colunas disponíveis:
1 - A Fazer (INITIAL)
2 - Em Andamento (PENDING)
3 - Concluído (FINAL)
4 - Cancelado (CANCELLATION)

Digite o ID da coluna para criar o card: 1

Card criado com sucesso! ID: 2
```

### Criar Card 3: Desenvolvimento Backend
```
Escolha uma opção: 3

Criar Novo Card
===============
Título do card: Desenvolvimento Backend
Descrição do card: Implementar API REST com Spring Boot

Colunas disponíveis:
1 - A Fazer (INITIAL)
2 - Em Andamento (PENDING)
3 - Concluído (FINAL)
4 - Cancelado (CANCELLATION)

Digite o ID da coluna para criar o card: 1

Card criado com sucesso! ID: 3
```

## Passo 5: Visualizar Board com Cards

```
Board: Projeto Web E-commerce
================================

Estado Atual do Board:
======================

A Fazer (INITIAL):
------------------
  ID: 1 - Análise de Requisitos do Sistema
      Descrição: Realizar levantamento completo dos requisitos funcionais e não funcionais
  ID: 2 - Desenvolvimento Frontend
      Descrição: Implementar interface do usuário com React
  ID: 3 - Desenvolvimento Backend
      Descrição: Implementar API REST com Spring Boot

Em Andamento (PENDING):
-----------------------
  (vazio)

Concluído (FINAL):
------------------
  (vazio)

Cancelado (CANCELLATION):
-------------------------
  (vazio)
```

## Passo 6: Mover Cards

### Mover Análise de Requisitos para Em Andamento
```
Escolha uma opção: 1

Digite o ID do card para mover: 1

Card movido com sucesso!
```

### Mover Desenvolvimento Frontend para Em Andamento
```
Escolha uma opção: 1

Digite o ID do card para mover: 2

Card movido com sucesso!
```

## Passo 7: Bloquear um Card

Vamos bloquear o desenvolvimento frontend por falta de design:

```
Escolha uma opção: 4

Digite o ID do card para bloquear: 2

Motivo do bloqueio: Aguardando aprovação do design pelo cliente

Card bloqueado com sucesso!
```

## Passo 8: Visualizar Board com Bloqueio

```
Board: Projeto Web E-commerce
================================

Estado Atual do Board:
======================

A Fazer (INITIAL):
------------------
  ID: 3 - Desenvolvimento Backend
      Descrição: Implementar API REST com Spring Boot

Em Andamento (PENDING):
-----------------------
  ID: 1 - Análise de Requisitos do Sistema
      Descrição: Realizar levantamento completo dos requisitos funcionais e não funcionais
  ID: 2 - Desenvolvimento Frontend [BLOQUEADO]
      Descrição: Implementar interface do usuário com React

Concluído (FINAL):
------------------
  (vazio)

Cancelado (CANCELLATION):
-------------------------
  (vazio)
```

## Passo 9: Desbloquear Card

Após receber o design aprovado:

```
Escolha uma opção: 5

Digite o ID do card para desbloquear: 2

Motivo do desbloqueio: Design aprovado pelo cliente

Card desbloqueado com sucesso!
```

## Passo 10: Continuar Movimentação

### Mover Análise de Requisitos para Concluído
```
Escolha uma opção: 1

Digite o ID do card para mover: 1

Card movido com sucesso!
```

### Mover Desenvolvimento Frontend para Concluído
```
Escolha uma opção: 1

Digite o ID do card para mover: 2

Card movido com sucesso!
```

## Passo 11: Cancelar um Card

Vamos cancelar o desenvolvimento backend por mudança de requisitos:

```
Escolha uma opção: 2

Digite o ID do card para cancelar: 3

Card cancelado com sucesso!
```

## Passo 12: Estado Final do Board

```
Board: Projeto Web E-commerce
================================

Estado Atual do Board:
======================

A Fazer (INITIAL):
------------------
  (vazio)

Em Andamento (PENDING):
-----------------------
  (vazio)

Concluído (FINAL):
------------------
  ID: 1 - Análise de Requisitos do Sistema
      Descrição: Realizar levantamento completo dos requisitos funcionais e não funcionais
  ID: 2 - Desenvolvimento Frontend
      Descrição: Implementar interface do usuário com React

Cancelado (CANCELLATION):
-------------------------
  ID: 3 - Desenvolvimento Backend
      Descrição: Implementar API REST com Spring Boot
```

## Passo 13: Gerar Relatórios

### Relatório de Conclusão
```
Escolha uma opção: 6

RELATÓRIO DE CONCLUSÃO DE TAREFAS - BOARD: Projeto Web E-commerce
===============================================================

CARD: Análise de Requisitos do Sistema
Descrição: Realizar levantamento completo dos requisitos funcionais e não funcionais
Data de Criação: 15/01/2024 10:30
Status Atual: Concluído
Tempo Total: 2d 5h 30min
Tempo por Coluna:
  - A Fazer: 1d 2h 15min
  - Em Andamento: 1d 3h 15min
  - Concluído: 0h 0min

----------------------------------------

CARD: Desenvolvimento Frontend
Descrição: Implementar interface do usuário com React
Data de Criação: 15/01/2024 10:35
Status Atual: Concluído
Tempo Total: 3d 8h 45min
Tempo por Coluna:
  - A Fazer: 0h 30min
  - Em Andamento: 2d 8h 15min
  - Concluído: 0h 0min

----------------------------------------

CARD: Desenvolvimento Backend
Descrição: Implementar API REST com Spring Boot
Data de Criação: 15/01/2024 10:40
Status Atual: Cancelado
Tempo Total: 1d 12h 20min
Tempo por Coluna:
  - A Fazer: 1d 12h 20min
  - Cancelado: 0h 0min

----------------------------------------
```

### Relatório de Bloqueios
```
Escolha uma opção: 7

RELATÓRIO DE BLOQUEIOS - BOARD: Projeto Web E-commerce
=====================================================

CARDS DESBLOQUEADOS:
----------------------------------------

CARD: Desenvolvimento Frontend
Data do Bloqueio: 16/01/2024 14:20
Data do Desbloqueio: 17/01/2024 09:15
Motivo do Bloqueio: Aguardando aprovação do design pelo cliente
Motivo do Desbloqueio: Design aprovado pelo cliente
Tempo Total Bloqueado: 18h 55min

----------------------------------------
```

## Benefícios Demonstrados

1. **Controle de Fluxo**: Cards seguem uma sequência lógica
2. **Rastreamento de Tempo**: Sistema calcula tempo em cada etapa
3. **Gestão de Bloqueios**: Controle de impedimentos com justificativas
4. **Relatórios Detalhados**: Visibilidade completa do progresso
5. **Flexibilidade**: Possibilidade de cancelar tarefas quando necessário

Este exemplo demonstra como o sistema pode ser usado para gerenciar projetos reais de forma eficiente e organizada. 