# Guia para Subir o Projeto no GitHub

## Passo a Passo Completo

### 1. Preparar o Repositório Local

```bash
# Inicializar git no projeto
git init

# Adicionar todos os arquivos
git add .

# Fazer o primeiro commit
git commit -m "Initial commit: Sistema de Gerenciamento de Tarefas

- Implementação completa do sistema de boards
- Arquitetura SOLID com Clean Architecture
- Persistência MySQL com JDBC
- Interface de usuário completa
- Relatórios de tempo e bloqueios
- Documentação completa"
```

### 2. Criar Repositório no GitHub

1. Acesse [github.com](https://github.com)
2. Clique em "New repository"
3. Configure:
   - **Repository name**: `task-board-manager` ou `sistema-gerenciamento-tarefas`
   - **Description**: `Sistema completo de board customizável para gerenciamento de tarefas desenvolvido em Java seguindo os princípios SOLID`
   - **Visibility**: Public (recomendado para portfólio)
   - **NÃO** marque "Add a README file" (já temos um)
   - **NÃO** marque "Add .gitignore" (já temos um)

### 3. Conectar Repositório Local ao GitHub

```bash
# Adicionar o repositório remoto (substitua SEU_USUARIO pelo seu username)
git remote add origin https://github.com/SEU_USUARIO/task-board-manager.git

# Enviar para o GitHub
git branch -M main
git push -u origin main
```

### 4. Configurar o README com Badges

Edite o `README.md` e adicione os badges no início:

```markdown
# Sistema de Gerenciamento de Tarefas

[![Java](https://img.shields.io/badge/Java-11-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-green.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20Architecture-blue.svg)](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
[![SOLID](https://img.shields.io/badge/Principles-SOLID-green.svg)](https://en.wikipedia.org/wiki/SOLID)

Sistema completo de board customizável para gerenciamento de tarefas desenvolvido em Java seguindo os princípios SOLID.
```

### 5. Configurar Topics no GitHub

No seu repositório no GitHub, adicione os seguintes topics:
- `java`
- `maven`
- `mysql`
- `jdbc`
- `solid-principles`
- `clean-architecture`
- `task-management`
- `kanban-board`
- `spring-boot`
- `database`

### 6. Configurar About Section

No GitHub, configure:
- **Description**: `Sistema completo de board customizável para gerenciamento de tarefas desenvolvido em Java seguindo os princípios SOLID`
- **Website**: (deixe em branco)
- **Topics**: (adicionados acima)

### 7. Criar Releases (Opcional)

```bash
# Criar uma tag para a versão
git tag -a v1.0.0 -m "Versão 1.0.0 - Sistema completo de gerenciamento de tarefas"

# Enviar a tag
git push origin v1.0.0
```

### 8. Configurar GitHub Pages (Opcional)

1. Vá em Settings > Pages
2. Source: Deploy from a branch
3. Branch: main
4. Folder: / (root)
5. Salve

### 9. Comandos Úteis para Manutenção

```bash
# Ver status
git status

# Ver histórico
git log --oneline

# Fazer alterações e commitar
git add .
git commit -m "Descrição das alterações"
git push

# Criar nova branch para features
git checkout -b feature/nova-funcionalidade
git push -u origin feature/nova-funcionalidade
```

## Estrutura Final no GitHub

Seu repositório deve ficar assim:

```
task-board-manager/
├── src/
│   ├── main/java/com/taskboard/
│   │   ├── domain/
│   │   ├── infrastructure/
│   │   └── application/
│   ├── test/
│   └── resources/
├── database/
├── .gitignore
├── LICENSE
├── README.md
├── RESUMO_PROJETO.md
├── INSTALACAO.md
├── EXEMPLO_USO.md
├── BADGES.md
├── GITHUB_SETUP.md
├── pom.xml
├── run.bat
└── run.sh
```

## Dicas para Deixar o Repositório Mais Profissional

1. **README atrativo** com badges e screenshots
2. **Documentação completa** em português
3. **Exemplos de uso** práticos
4. **Guia de instalação** detalhado
5. **Licença** clara (MIT)
6. **Topics** relevantes
7. **Commits** com mensagens descritivas
8. **Releases** versionadas

## Links Úteis

- [GitHub Guides](https://guides.github.com/)
- [GitHub Markdown](https://docs.github.com/en/github/writing-on-github)
- [Shields.io](https://shields.io/) (para badges)
- [Choose a License](https://choosealicense.com/)

Agora seu projeto estará no GitHub com uma aparência profissional e adequada para portfólio! 