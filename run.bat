@echo off
echo ========================================
echo Sistema de Gerenciamento de Tarefas
echo ========================================
echo.

echo Verificando se o Maven esta instalado...
mvn --version >nul 2>&1
if errorlevel 1 (
    echo ERRO: Maven nao encontrado. Instale o Maven primeiro.
    pause
    exit /b 1
)

echo.
echo Compilando o projeto...
mvn clean compile
if errorlevel 1 (
    echo ERRO: Falha na compilacao.
    pause
    exit /b 1
)

echo.
echo Executando o sistema...
mvn exec:java -Dexec.mainClass="com.taskboard.TaskBoardApplication"

pause 