#!/bin/bash

echo "========================================"
echo "Sistema de Gerenciamento de Tarefas"
echo "========================================"
echo

echo "Verificando se o Maven está instalado..."
if ! command -v mvn &> /dev/null; then
    echo "ERRO: Maven não encontrado. Instale o Maven primeiro."
    exit 1
fi

echo
echo "Compilando o projeto..."
mvn clean compile
if [ $? -ne 0 ]; then
    echo "ERRO: Falha na compilação."
    exit 1
fi

echo
echo "Executando o sistema..."
mvn exec:java -Dexec.mainClass="com.taskboard.TaskBoardApplication" 