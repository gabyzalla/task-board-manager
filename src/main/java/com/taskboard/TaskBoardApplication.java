package com.taskboard;

import com.taskboard.application.ui.UserInterface;
import com.taskboard.domain.repositories.*;
import com.taskboard.domain.services.BoardService;
import com.taskboard.domain.services.CardService;
import com.taskboard.domain.services.ReportService;
import com.taskboard.infrastructure.database.DatabaseManager;
import com.taskboard.infrastructure.repositories.*;

public class TaskBoardApplication {
    public static void main(String[] args) {
        try {
            System.out.println("Inicializando Sistema de Gerenciamento de Tarefas...");
            
            // Inicializar banco de dados
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            
            // Criar repositórios
            BoardRepository boardRepository = new MySQLBoardRepository(databaseManager.getConnection());
            ColumnRepository columnRepository = new MySQLColumnRepository(databaseManager.getConnection());
            CardRepository cardRepository = new MySQLCardRepository(databaseManager.getConnection());
            CardMovementRepository cardMovementRepository = new MySQLCardMovementRepository(databaseManager.getConnection());
            
            // Criar serviços
            BoardService boardService = new BoardService(boardRepository, columnRepository);
            CardService cardService = new CardService(cardRepository, cardMovementRepository);
            ReportService reportService = new ReportService(cardService, cardRepository);
            
            // Criar interface de usuário
            UserInterface userInterface = new UserInterface(boardService, cardService, reportService);
            
            // Iniciar aplicação
            userInterface.start();
            
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o sistema: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
} 