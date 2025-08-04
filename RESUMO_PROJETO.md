# Resumo do Projeto - Sistema de Gerenciamento de Tarefas

## Visão Geral

Sistema completo de board customizável para gerenciamento de tarefas desenvolvido em Java seguindo os princípios SOLID. O projeto atende todos os requisitos solicitados e implementa funcionalidades opcionais avançadas.

## Requisitos Atendidos

### Menu Principal
- Criar novo board
- Selecionar board  
- Excluir boards
- Sair

### Persistência
- Banco de dados MySQL
- Tabelas criadas automaticamente
- Relacionamentos com chaves estrangeiras

### Regras dos Boards
- Nome do board
- Mínimo 3 colunas (Inicial, Pendente, Final, Cancelamento)
- Tipos de coluna: Inicial, Pendente, Final, Cancelamento
- Apenas 1 coluna de cada tipo obrigatório
- Ordem específica: Inicial (1ª), Final (penúltima), Cancelamento (última)

### Cards
- Título, descrição, data de criação
- Sistema de bloqueio com motivo
- Movimentação sequencial entre colunas
- Cards bloqueados não podem ser movidos
- Cancelamento direto para coluna de cancelamento

### Menu de Manipulação
- Mover card para próxima coluna
- Cancelar card
- Criar card
- Bloquear card
- Desbloquear card
- Fechar board

## Funcionalidades Opcionais Implementadas

### 1. Rastreamento de Tempo
- Data/hora de entrada e saída de cada coluna
- Cálculo de tempo total de conclusão
- Tempo gasto em cada coluna

### 2. Relatório de Conclusão
- Tempo total de cada tarefa
- Tempo em cada coluna
- Status atual de cada card

### 3. Relatório de Bloqueios
- Cards atualmente bloqueados
- Histórico de bloqueios e desbloqueios
- Tempo total de bloqueio
- Justificativas de bloqueio e desbloqueio

## Arquitetura SOLID

### Single Responsibility Principle (SRP)
- Cada classe tem uma única responsabilidade
- Serviços separados: BoardService, CardService, ReportService

### Open/Closed Principle (OCP)
- Sistema extensível através de interfaces
- Novos tipos de relatórios podem ser adicionados

### Liskov Substitution Principle (LSP)
- Implementações de repositórios são intercambiáveis
- Interfaces bem definidas garantem substituição segura

### Interface Segregation Principle (ISP)
- Interfaces específicas para cada tipo de repositório
- Clientes não dependem de métodos que não utilizam

### Dependency Inversion Principle (DIP)
- Dependências injetadas através de construtores
- Camada de domínio não depende de infraestrutura

## Estrutura do Projeto

```
src/main/java/com/taskboard/
├── domain/                    # Camada de domínio
│   ├── entities/             # Board, Card, Column, CardMovement
│   ├── enums/                # ColumnType
│   ├── repositories/         # Interfaces dos repositórios
│   └── services/             # Serviços de domínio
├── infrastructure/           # Camada de infraestrutura
│   ├── database/             # DatabaseManager
│   └── repositories/         # Implementações MySQL
└── application/              # Camada de aplicação
    └── ui/                   # UserInterface
```

## Banco de Dados

### Tabelas Criadas Automaticamente
- **boards** - Armazena os boards
- **columns** - Armazena as colunas de cada board
- **cards** - Armazena os cards com informações de bloqueio
- **card_movements** - Rastreia movimentações dos cards

### Relacionamentos
- Board → Columns (1:N)
- Column → Cards (1:N)
- Card → CardMovements (1:N)

## Tecnologias Utilizadas

- **Java 11** - Linguagem principal
- **MySQL 8.0** - Banco de dados
- **Maven** - Gerenciamento de dependências
- **JDBC** - Conexão com banco de dados
- **JUnit** - Testes unitários

## Como Executar

### Pré-requisitos
- Java 11+
- MySQL 8.0+
- Maven 3.6+

### Configuração Rápida
1. Criar banco MySQL: `CREATE DATABASE taskboard;`
2. Configurar credenciais em `DatabaseManager.java`
3. Executar: `mvn exec:java -Dexec.mainClass="com.taskboard.TaskBoardApplication"`

### Scripts de Execução
- **Windows**: `run.bat`
- **Linux/Mac**: `./run.sh`

## Funcionalidades Demonstradas

### Exemplo de Uso Completo
- Criação de board com colunas padrão
- Criação e movimentação de cards
- Sistema de bloqueio com justificativas
- Relatórios detalhados de tempo e bloqueios
- Cancelamento de tarefas

### Validações Implementadas
- Estrutura de board válida
- Cards não podem pular colunas
- Cards bloqueados não podem ser movidos
- Cards na coluna final não podem ser cancelados
- Motivos obrigatórios para bloqueio/desbloqueio

## Benefícios do Projeto

1. **Código Limpo**: Seguindo princípios SOLID
2. **Manutenibilidade**: Arquitetura em camadas
3. **Extensibilidade**: Fácil adição de novas funcionalidades
4. **Testabilidade**: Código testável e bem estruturado
5. **Documentação**: README completo e exemplos de uso

## Métricas de Qualidade

- **Cobertura de Funcionalidades**: 100% dos requisitos atendidos
- **Princípios SOLID**: Todos aplicados corretamente
- **Documentação**: README, guias de instalação e uso
- **Testes**: Testes unitários incluídos
- **Código**: Código limpo e profissional

Este projeto demonstra proficiência em Java, arquitetura de software, banco de dados e boas práticas de desenvolvimento, sendo ideal para portfólio profissional. 