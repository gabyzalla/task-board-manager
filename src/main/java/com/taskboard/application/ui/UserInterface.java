package com.taskboard.application.ui;

import com.taskboard.domain.entities.Board;
import com.taskboard.domain.entities.Card;
import com.taskboard.domain.entities.Column;
import com.taskboard.domain.services.BoardService;
import com.taskboard.domain.services.CardService;
import com.taskboard.domain.services.ReportService;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final BoardService boardService;
    private final CardService cardService;
    private final ReportService reportService;
    private Board currentBoard;

    public UserInterface(BoardService boardService, CardService cardService, ReportService reportService) {
        this.scanner = new Scanner(System.in);
        this.boardService = boardService;
        this.cardService = cardService;
        this.reportService = reportService;
    }

    public void start() {
        System.out.println("Sistema de Gerenciamento de Tarefas");
        System.out.println("===================================");
        
        while (true) {
            showMainMenu();
            int choice = getIntInput("Escolha uma opção: ");
            
            try {
                switch (choice) {
                    case 1:
                        createNewBoard();
                        break;
                    case 2:
                        selectBoard();
                        break;
                    case 3:
                        deleteBoards();
                        break;
                    case 4:
                        System.out.println("Saindo do sistema...");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            
            System.out.println();
        }
    }

    private void showMainMenu() {
        System.out.println("\nMenu Principal:");
        System.out.println("1 - Criar novo board");
        System.out.println("2 - Selecionar board");
        System.out.println("3 - Excluir boards");
        System.out.println("4 - Sair");
    }

    private void createNewBoard() {
        System.out.println("\nCriar Novo Board");
        System.out.println("================");
        
        String name = getStringInput("Digite o nome do board: ");
        
        Board board = boardService.createBoardWithDefaultColumns(name);
        System.out.println("Board '" + board.getName() + "' criado com sucesso!");
        System.out.println("Colunas criadas:");
        for (Column column : board.getColumns()) {
            System.out.println("- " + column.getName() + " (" + column.getType() + ")");
        }
    }

    private void selectBoard() {
        List<Board> boards = boardService.getAllBoards();
        
        if (boards.isEmpty()) {
            System.out.println("Nenhum board encontrado. Crie um board primeiro.");
            return;
        }
        
        System.out.println("\nBoards Disponíveis:");
        System.out.println("===================");
        
        for (int i = 0; i < boards.size(); i++) {
            Board board = boards.get(i);
            System.out.println((i + 1) + " - " + board.getName() + " (" + board.getColumns().size() + " colunas)");
        }
        
        int choice = getIntInput("Escolha o board (0 para voltar): ");
        
        if (choice == 0) {
            return;
        }
        
        if (choice > 0 && choice <= boards.size()) {
            currentBoard = boards.get(choice - 1);
            showBoardMenu();
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void deleteBoards() {
        List<Board> boards = boardService.getAllBoards();
        
        if (boards.isEmpty()) {
            System.out.println("Nenhum board encontrado.");
            return;
        }
        
        System.out.println("\nExcluir Boards");
        System.out.println("==============");
        
        for (int i = 0; i < boards.size(); i++) {
            Board board = boards.get(i);
            System.out.println((i + 1) + " - " + board.getName());
        }
        
        int choice = getIntInput("Escolha o board para excluir (0 para voltar): ");
        
        if (choice == 0) {
            return;
        }
        
        if (choice > 0 && choice <= boards.size()) {
            Board boardToDelete = boards.get(choice - 1);
            String confirm = getStringInput("Tem certeza que deseja excluir o board '" + boardToDelete.getName() + "'? (s/n): ");
            
            if (confirm.equalsIgnoreCase("s")) {
                boardService.deleteBoard(boardToDelete.getId());
                System.out.println("Board excluído com sucesso!");
                
                if (currentBoard != null && currentBoard.getId().equals(boardToDelete.getId())) {
                    currentBoard = null;
                }
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void showBoardMenu() {
        while (true) {
            System.out.println("\nBoard: " + currentBoard.getName());
            System.out.println("================================");
            displayBoard();
            
            System.out.println("\nMenu do Board:");
            System.out.println("1 - Mover card para próxima coluna");
            System.out.println("2 - Cancelar card");
            System.out.println("3 - Criar card");
            System.out.println("4 - Bloquear card");
            System.out.println("5 - Desbloquear card");
            System.out.println("6 - Gerar relatório de conclusão");
            System.out.println("7 - Gerar relatório de bloqueios");
            System.out.println("8 - Voltar ao menu principal");
            
            int choice = getIntInput("Escolha uma opção: ");
            
            try {
                switch (choice) {
                    case 1:
                        moveCardToNextColumn();
                        break;
                    case 2:
                        cancelCard();
                        break;
                    case 3:
                        createCard();
                        break;
                    case 4:
                        blockCard();
                        break;
                    case 5:
                        unblockCard();
                        break;
                    case 6:
                        generateCompletionReport();
                        break;
                    case 7:
                        generateBlockReport();
                        break;
                    case 8:
                        currentBoard = null;
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void displayBoard() {
        System.out.println("\nEstado Atual do Board:");
        System.out.println("======================");
        
        for (Column column : currentBoard.getColumns()) {
            System.out.println("\n" + column.getName() + " (" + column.getType() + "):");
            System.out.println("-".repeat(column.getName().length() + 10));
            
            List<Card> cards = cardService.getCardsByColumnId(column.getId());
            if (cards.isEmpty()) {
                System.out.println("  (vazio)");
            } else {
                for (Card card : cards) {
                    String status = card.isBlocked() ? "[BLOQUEADO]" : "";
                    System.out.println("  ID: " + card.getId() + " - " + card.getTitle() + " " + status);
                    System.out.println("      Descrição: " + card.getDescription());
                }
            }
        }
    }

    private void moveCardToNextColumn() {
        Long cardId = getCardIdInput("Digite o ID do card para mover: ");
        cardService.moveCardToNextColumn(cardId, currentBoard);
        System.out.println("Card movido com sucesso!");
    }

    private void cancelCard() {
        Long cardId = getCardIdInput("Digite o ID do card para cancelar: ");
        cardService.cancelCard(cardId, currentBoard);
        System.out.println("Card cancelado com sucesso!");
    }

    private void createCard() {
        System.out.println("\nCriar Novo Card");
        System.out.println("===============");
        
        String title = getStringInput("Título do card: ");
        String description = getStringInput("Descrição do card: ");
        
        System.out.println("\nColunas disponíveis:");
        for (Column column : currentBoard.getColumns()) {
            System.out.println(column.getId() + " - " + column.getName() + " (" + column.getType() + ")");
        }
        
        Long columnId = getLongInput("Digite o ID da coluna para criar o card: ");
        
        Card card = cardService.createCard(title, description, columnId, currentBoard);
        System.out.println("Card criado com sucesso! ID: " + card.getId());
    }

    private void blockCard() {
        Long cardId = getCardIdInput("Digite o ID do card para bloquear: ");
        String reason = getStringInput("Motivo do bloqueio: ");
        
        cardService.blockCard(cardId, reason);
        System.out.println("Card bloqueado com sucesso!");
    }

    private void unblockCard() {
        Long cardId = getCardIdInput("Digite o ID do card para desbloquear: ");
        String reason = getStringInput("Motivo do desbloqueio: ");
        
        cardService.unblockCard(cardId, reason);
        System.out.println("Card desbloqueado com sucesso!");
    }

    private void generateCompletionReport() {
        String report = reportService.generateTaskCompletionReport(currentBoard);
        System.out.println("\n" + report);
    }

    private void generateBlockReport() {
        String report = reportService.generateBlockReport(currentBoard);
        System.out.println("\n" + report);
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private Long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private Long getCardIdInput(String prompt) {
        return getLongInput(prompt);
    }
} 