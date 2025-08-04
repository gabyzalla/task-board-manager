# Guia de Instalação - Sistema de Gerenciamento de Tarefas

## Passo a Passo para Configuração

### 1. Pré-requisitos

Certifique-se de ter instalado:
- **Java 11 ou superior**
- **MySQL 8.0 ou superior**
- **Maven 3.6 ou superior**

### 2. Configuração do MySQL

#### 2.1. Criar o Banco de Dados

Execute no MySQL:
```sql
CREATE DATABASE taskboard CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Ou use o script fornecido:
```bash
mysql -u root -p < database/init.sql
```

#### 2.2. Criar Usuário (Opcional)

Para maior segurança, crie um usuário específico:
```sql
CREATE USER 'taskboard_user'@'localhost' IDENTIFIED BY 'sua_senha_segura';
GRANT ALL PRIVILEGES ON taskboard.* TO 'taskboard_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configuração da Aplicação

#### 3.1. Editar Configurações de Conexão

Edite o arquivo `src/main/java/com/taskboard/infrastructure/database/DatabaseManager.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/taskboard";
private static final String USER = "seu_usuario";        // root ou taskboard_user
private static final String PASSWORD = "sua_senha";      // sua senha do MySQL
```

#### 3.2. Verificar Dependências

Certifique-se de que o arquivo `pom.xml` está correto e execute:
```bash
mvn dependency:resolve
```

### 4. Compilação e Execução

#### 4.1. Compilar o Projeto
```bash
mvn clean compile
```

#### 4.2. Executar o Sistema

**Opção 1 - Usando Maven:**
```bash
mvn exec:java -Dexec.mainClass="com.taskboard.TaskBoardApplication"
```

**Opção 2 - Criar JAR e Executar:**
```bash
mvn clean package
java -jar target/task-board-manager-1.0.0.jar
```

### 5. Verificação da Instalação

Após executar o sistema, você deve ver:
```
Inicializando Sistema de Gerenciamento de Tarefas...
Sistema de Gerenciamento de Tarefas
===================================

Menu Principal:
1 - Criar novo board
2 - Selecionar board
3 - Excluir boards
4 - Sair
```

### 6. Primeiro Uso

1. **Criar um Board:**
   - Escolha opção 1 no menu principal
   - Digite um nome para o board
   - O sistema criará automaticamente as colunas padrão

2. **Testar Funcionalidades:**
   - Selecione o board criado
   - Crie alguns cards
   - Teste a movimentação entre colunas
   - Teste o sistema de bloqueio

### 7. Solução de Problemas

#### Erro de Conexão com MySQL
```
Erro ao inicializar o sistema: Communications link failure
```
**Solução:** Verifique se o MySQL está rodando e as credenciais estão corretas.

#### Erro de Compilação
```
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin
```
**Solução:** Verifique se o Java 11+ está instalado e configurado.

#### Erro de Dependências
```
[ERROR] Could not resolve dependencies
```
**Solução:** Execute `mvn clean install` para baixar todas as dependências.

### 8. Configurações Avançadas

#### 8.1. Alterar Porta do MySQL
Se o MySQL não estiver na porta padrão (3306), edite a URL:
```java
private static final String URL = "jdbc:mysql://localhost:3307/taskboard";
```

#### 8.2. Configurar Timeout de Conexão
Adicione parâmetros à URL:
```java
private static final String URL = "jdbc:mysql://localhost:3306/taskboard?connectTimeout=30000&socketTimeout=30000";
```

### 9. Backup e Restauração

#### 9.1. Backup do Banco
```bash
mysqldump -u root -p taskboard > backup_taskboard.sql
```

#### 9.2. Restaurar Backup
```bash
mysql -u root -p taskboard < backup_taskboard.sql
```

### 10. Logs e Debug

Para habilitar logs detalhados, adicione ao código:
```java
System.setProperty("java.util.logging.config.file", "logging.properties");
```

---

## Suporte

Se encontrar problemas durante a instalação:
1. Verifique se todos os pré-requisitos estão instalados
2. Confirme as configurações de conexão com o MySQL
3. Verifique os logs de erro para mais detalhes
4. Consulte a documentação do MySQL se necessário 