package com.taskboard.domain.repositories;

import com.taskboard.domain.entities.CardMovement;
import java.util.List;

public interface CardMovementRepository {
    CardMovement save(CardMovement movement);
    List<CardMovement> findByCardId(Long cardId);
    List<CardMovement> findByBoardId(Long boardId);
    void deleteByCardId(Long cardId);
} 