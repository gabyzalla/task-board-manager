package com.taskboard.domain.services;

import com.taskboard.domain.entities.Board;
import com.taskboard.domain.entities.Card;
import com.taskboard.domain.entities.Column;
import com.taskboard.domain.repositories.CardRepository;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService {
    private final CardService cardService;
    private final CardRepository cardRepository;

    public ReportService(CardService cardService, CardRepository cardRepository) {
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

    public String generateTaskCompletionReport(Board board) {
        StringBuilder report = new StringBuilder();
        report.append("RELATÓRIO DE CONCLUSÃO DE TAREFAS - BOARD: ").append(board.getName()).append("\n");
        report.append("=".repeat(60)).append("\n\n");

        List<Card> allCards = cardRepository.findByBoardId(board.getId());
        
        for (Card card : allCards) {
            report.append("CARD: ").append(card.getTitle()).append("\n");
            report.append("Descrição: ").append(card.getDescription()).append("\n");
            report.append("Data de Criação: ").append(card.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
            
            Column currentColumn = board.getColumnById(card.getCurrentColumnId());
            report.append("Status Atual: ").append(currentColumn != null ? currentColumn.getName() : "N/A").append("\n");
            
            long totalTime = calculateTotalCompletionTime(card, board);
            report.append("Tempo Total: ").append(formatTime(totalTime)).append("\n");
            
            report.append("Tempo por Coluna:\n");
            for (Column column : board.getColumns()) {
                long timeInColumn = cardService.getCardTimeInColumn(card, column.getId());
                if (timeInColumn > 0) {
                    report.append("  - ").append(column.getName()).append(": ").append(formatTime(timeInColumn)).append("\n");
                }
            }
            
            report.append("-".repeat(40)).append("\n\n");
        }

        return report.toString();
    }

    public String generateBlockReport(Board board) {
        StringBuilder report = new StringBuilder();
        report.append("RELATÓRIO DE BLOQUEIOS - BOARD: ").append(board.getName()).append("\n");
        report.append("=".repeat(60)).append("\n\n");

        List<Card> allCards = cardRepository.findByBoardId(board.getId());
        List<Card> blockedCards = allCards.stream()
                .filter(Card::isBlocked)
                .collect(Collectors.toList());

        if (blockedCards.isEmpty()) {
            report.append("Nenhum card bloqueado encontrado.\n");
        } else {
            for (Card card : blockedCards) {
                report.append("CARD: ").append(card.getTitle()).append("\n");
                report.append("Descrição: ").append(card.getDescription()).append("\n");
                report.append("Data do Bloqueio: ").append(card.getBlockDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
                report.append("Motivo do Bloqueio: ").append(card.getBlockReason()).append("\n");
                
                long blockTime = cardService.getCardBlockTime(card);
                report.append("Tempo Bloqueado: ").append(formatTime(blockTime)).append("\n");
                
                Column currentColumn = board.getColumnById(card.getCurrentColumnId());
                report.append("Coluna Atual: ").append(currentColumn != null ? currentColumn.getName() : "N/A").append("\n");
                
                report.append("-".repeat(40)).append("\n\n");
            }
        }

        List<Card> unblockedCards = allCards.stream()
                .filter(card -> !card.isBlocked() && card.getBlockDate() != null)
                .collect(Collectors.toList());

        if (!unblockedCards.isEmpty()) {
            report.append("CARDS DESBLOQUEADOS:\n");
            report.append("-".repeat(40)).append("\n");
            
            for (Card card : unblockedCards) {
                report.append("CARD: ").append(card.getTitle()).append("\n");
                report.append("Data do Bloqueio: ").append(card.getBlockDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
                report.append("Data do Desbloqueio: ").append(card.getUnblockDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
                report.append("Motivo do Bloqueio: ").append(card.getBlockReason()).append("\n");
                report.append("Motivo do Desbloqueio: ").append(card.getUnblockReason()).append("\n");
                
                long totalBlockTime = cardService.getCardBlockTime(card);
                report.append("Tempo Total Bloqueado: ").append(formatTime(totalBlockTime)).append("\n");
                
                report.append("-".repeat(40)).append("\n\n");
            }
        }

        return report.toString();
    }

    private long calculateTotalCompletionTime(Card card, Board board) {
        Column finalColumn = board.getFinalColumn();
        if (finalColumn == null) {
            return 0;
        }

        CardMovement finalMovement = card.getMovements().stream()
                .filter(m -> m.getToColumnId().equals(finalColumn.getId()))
                .findFirst()
                .orElse(null);

        if (finalMovement == null) {
            return 0;
        }

        return java.time.Duration.between(card.getCreationDate(), finalMovement.getMovementDate()).toMinutes();
    }

    private String formatTime(long minutes) {
        if (minutes < 60) {
            return minutes + " minutos";
        } else if (minutes < 1440) {
            long hours = minutes / 60;
            long remainingMinutes = minutes % 60;
            return hours + "h " + remainingMinutes + "min";
        } else {
            long days = minutes / 1440;
            long remainingHours = (minutes % 1440) / 60;
            long remainingMinutes = minutes % 60;
            return days + "d " + remainingHours + "h " + remainingMinutes + "min";
        }
    }
} 