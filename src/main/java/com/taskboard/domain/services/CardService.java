package com.taskboard.domain.services;

import com.taskboard.domain.entities.Board;
import com.taskboard.domain.entities.Card;
import com.taskboard.domain.entities.CardMovement;
import com.taskboard.domain.entities.Column;
import com.taskboard.domain.enums.ColumnType;
import com.taskboard.domain.repositories.CardRepository;
import com.taskboard.domain.repositories.CardMovementRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class CardService {
    private final CardRepository cardRepository;
    private final CardMovementRepository movementRepository;

    public CardService(CardRepository cardRepository, CardMovementRepository movementRepository) {
        this.cardRepository = cardRepository;
        this.movementRepository = movementRepository;
    }

    public Card createCard(String title, String description, Long columnId, Board board) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Título do card não pode ser vazio");
        }

        Column column = board.getColumnById(columnId);
        if (column == null) {
            throw new IllegalArgumentException("Coluna não encontrada");
        }

        Card card = new Card(title, description);
        card.setCurrentColumnId(columnId);
        
        Card savedCard = cardRepository.save(card);
        column.addCard(savedCard);
        
        return savedCard;
    }

    public void moveCardToNextColumn(Long cardId, Board board) {
        Card card = findCardById(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card não encontrado");
        }

        if (card.isBlocked()) {
            throw new IllegalArgumentException("Card está bloqueado e não pode ser movido");
        }

        Column currentColumn = board.getColumnById(card.getCurrentColumnId());
        if (currentColumn == null) {
            throw new IllegalArgumentException("Coluna atual não encontrada");
        }

        Column nextColumn = board.getNextColumn(currentColumn);
        if (nextColumn == null) {
            throw new IllegalArgumentException("Não há próxima coluna disponível");
        }

        if (nextColumn.getType() == ColumnType.CANCELLATION) {
            throw new IllegalArgumentException("Cards não podem ser movidos diretamente para a coluna de cancelamento");
        }

        moveCard(card, currentColumn, nextColumn);
    }

    public void cancelCard(Long cardId, Board board) {
        Card card = findCardById(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card não encontrado");
        }

        Column currentColumn = board.getColumnById(card.getCurrentColumnId());
        if (currentColumn == null) {
            throw new IllegalArgumentException("Coluna atual não encontrada");
        }

        Column cancellationColumn = board.getCancellationColumn();
        if (cancellationColumn == null) {
            throw new IllegalArgumentException("Coluna de cancelamento não encontrada");
        }

        if (currentColumn.getType() == ColumnType.FINAL) {
            throw new IllegalArgumentException("Cards na coluna final não podem ser cancelados");
        }

        moveCard(card, currentColumn, cancellationColumn);
    }

    public void blockCard(Long cardId, String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo do bloqueio é obrigatório");
        }

        Card card = findCardById(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card não encontrado");
        }

        if (card.isBlocked()) {
            throw new IllegalArgumentException("Card já está bloqueado");
        }

        card.block(reason);
        cardRepository.save(card);
    }

    public void unblockCard(Long cardId, String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo do desbloqueio é obrigatório");
        }

        Card card = findCardById(cardId);
        if (card == null) {
            throw new IllegalArgumentException("Card não encontrado");
        }

        if (!card.isBlocked()) {
            throw new IllegalArgumentException("Card não está bloqueado");
        }

        card.unblock(reason);
        cardRepository.save(card);
    }

    public Card findCardById(Long cardId) {
        Optional<Card> cardOpt = cardRepository.findById(cardId);
        if (cardOpt.isPresent()) {
            Card card = cardOpt.get();
            List<CardMovement> movements = movementRepository.findByCardId(cardId);
            card.setMovements(movements);
        }
        return cardOpt.orElse(null);
    }

    public List<Card> getCardsByColumnId(Long columnId) {
        return cardRepository.findByColumnId(columnId);
    }

    public List<Card> getCardsByBoardId(Long boardId) {
        return cardRepository.findByBoardId(boardId);
    }

    public void deleteCard(Long cardId) {
        if (!cardRepository.findById(cardId).isPresent()) {
            throw new IllegalArgumentException("Card não encontrado");
        }
        
        movementRepository.deleteByCardId(cardId);
        cardRepository.deleteById(cardId);
    }

    private void moveCard(Card card, Column fromColumn, Column toColumn) {
        fromColumn.removeCard(card);
        toColumn.addCard(card);
        
        CardMovement movement = new CardMovement(card.getId(), fromColumn.getId(), toColumn.getId());
        movementRepository.save(movement);
        card.addMovement(movement);
        
        cardRepository.save(card);
    }

    public long getCardTimeInColumn(Card card, Long columnId) {
        CardMovement entryMovement = card.getMovements().stream()
                .filter(m -> m.getToColumnId().equals(columnId))
                .findFirst()
                .orElse(null);

        CardMovement exitMovement = card.getMovements().stream()
                .filter(m -> m.getFromColumnId().equals(columnId))
                .findFirst()
                .orElse(null);

        LocalDateTime entryTime = entryMovement != null ? entryMovement.getMovementDate() : card.getCreationDate();
        LocalDateTime exitTime = exitMovement != null ? exitMovement.getMovementDate() : LocalDateTime.now();

        return ChronoUnit.MINUTES.between(entryTime, exitTime);
    }

    public long getCardBlockTime(Card card) {
        if (!card.isBlocked() || card.getBlockDate() == null) {
            return 0;
        }

        LocalDateTime blockTime = card.getBlockDate();
        LocalDateTime unblockTime = card.getUnblockDate() != null ? card.getUnblockDate() : LocalDateTime.now();

        return ChronoUnit.MINUTES.between(blockTime, unblockTime);
    }
} 