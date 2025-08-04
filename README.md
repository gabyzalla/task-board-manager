# Sistema de Gerenciamento de Tarefas

Sistema completo de board customizável para acompanhamento de tarefas desenvolvido em Java seguindo os princípios SOLID.

## Descrição

Este projeto implementa um sistema de gerenciamento de tarefas com boards customizáveis, permitindo o controle completo do fluxo de trabalho através de colunas configuráveis e cards com funcionalidades avançadas de bloqueio e rastreamento de tempo.

## Funcionalidades

### Principais
- Criação e gerenciamento de boards customizáveis
- Sistema de colunas com tipos específicos (Inicial, Pendente, Final, Cancelamento)
- Criação e movimentação de cards entre colunas
- Sistema de bloqueio de cards com justificativas
- Relatórios detalhados de conclusão e bloqueios
- Persistência completa em banco de dados MySQL

### Regras de Negócio
- Boards devem ter pelo menos 3 colunas
- Apenas uma coluna de cada tipo (Inicial, Final, Cancelamento) por board
- Coluna inicial deve ser a primeira, final a penúltima, cancelamento a última
- Cards só podem ser movidos para a próxima coluna sequencial
- Cards bloqueados não podem ser movidos
- Cards podem ser cancelados de qualquer coluna exceto a final
- Rastreamento completo de tempo em cada coluna

## Arquitetura

O projeto segue os princípios SOLID e utiliza uma arquitetura em camadas:

### Estrutura de Pacotes
```
src/main/java/com/taskboard/
├── domain/                    # Camada de domínio
│   ├── entities/             # Entidades de negócio
│   ├── enums/                # Enumerações
│   ├── repositories/         # Interfaces dos repositórios
│   └── services/             # Serviços de domínio
├── infrastructure/           # Camada de infraestrutura
│   ├── database/             # Gerenciamento de banco
│   └── repositories/         # Implementações MySQL
└── application/              # Camada de aplicação
    └── ui/                   # Interface de usuário
```

### Princípios SOLID Aplicados

1. **Single Responsibility Principle (SRP)**
   - Cada classe tem uma única responsabilidade
   - Serviços separados para Board, Card e Relatórios

2. **Open/Closed Principle (OCP)**
   - Sistema extensível através de interfaces
   - Novos tipos de relatórios podem ser adicionados facilmente

3. **Liskov Substitution Principle (LSP)**
   - Implementações de repositórios são intercambiáveis
   - Interfaces bem definidas garantem substituição segura

4. **Interface Segregation Principle (ISP)**
   - Interfaces específicas para cada tipo de repositório
   - Clientes não dependem de métodos que não utilizam

5. **Dependency Inversion Principle (DIP)**
   - Dependências injetadas através de construtores
   - Camada de domínio não depende de infraestrutura

## Pré-requisitos

- Java 11 ou superior
- MySQL 8.0 ou superior
- Maven 3.6 ou superior

## Configuração

### 1. Banco de Dados MySQL

Crie um banco de dados MySQL:
```sql
CREATE DATABASE taskboard;
```

### 2. Configuração de Conexão

Edite a classe `DatabaseManager` em `src/main/java/com/taskboard/infrastructure/database/DatabaseManager.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/taskboard";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### 3. Compilação e Execução

```bash
# Compilar o projeto
mvn clean compile

# Executar o sistema
mvn exec:java -Dexec.mainClass="com.taskboard.TaskBoardApplication"
```

Ou usando o JAR:
```bash
# Criar JAR executável
mvn clean package

# Executar
java -jar target/task-board-manager-1.0.0.jar
```

## Como Usar

### Menu Principal
1. **Criar novo board** - Cria um board com colunas padrão
2. **Selecionar board** - Acessa um board existente
3. **Excluir boards** - Remove boards do sistema
4. **Sair** - Encerra o sistema

### Menu do Board
1. **Mover card para próxima coluna** - Avança um card no fluxo
2. **Cancelar card** - Move card para coluna de cancelamento
3. **Criar card** - Adiciona novo card ao board
4. **Bloquear card** - Bloqueia card com justificativa
5. **Desbloquear card** - Remove bloqueio com justificativa
6. **Gerar relatório de conclusão** - Relatório de tempo por coluna
7. **Gerar relatório de bloqueios** - Relatório de bloqueios e desbloqueios
8. **Voltar ao menu principal** - Retorna ao menu principal

## Estrutura do Banco de Dados

### Tabelas Criadas Automaticamente

- **boards** - Armazena os boards
- **columns** - Armazena as colunas de cada board
- **cards** - Armazena os cards com informações de bloqueio
- **card_movements** - Rastreia movimentações dos cards

## Funcionalidades Opcionais Implementadas

1. **Rastreamento de Tempo**
   - Data e hora de entrada e saída de cada coluna
   - Cálculo de tempo total de conclusão
   - Tempo gasto em cada coluna

2. **Relatório de Conclusão**
   - Tempo total de cada tarefa
   - Tempo em cada coluna
   - Status atual de cada card

3. **Relatório de Bloqueios**
   - Cards atualmente bloqueados
   - Histórico de bloqueios e desbloqueios
   - Tempo total de bloqueio
   - Justificativas de bloqueio e desbloqueio

## Tecnologias Utilizadas

- **Java 11** - Linguagem principal
- **MySQL** - Banco de dados
- **Maven** - Gerenciamento de dependências
- **JDBC** - Conexão com banco de dados

## Contribuição

Este projeto foi desenvolvido como projeto final seguindo os princípios SOLID e boas práticas de desenvolvimento. Para contribuições:

1. Mantenha a arquitetura em camadas
2. Siga os princípios SOLID
3. Adicione testes para novas funcionalidades
4. Documente mudanças significativas

## Licença

Este projeto é de uso educacional e pode ser utilizado livremente para fins de aprendizado e portfólio. 