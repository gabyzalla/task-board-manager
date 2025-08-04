package com.taskboard.domain.repositories;

import com.taskboard.domain.entities.Card;
import java.util.List;
import java.util.Optional;

public interface CardRepository {
    Card save(Card card);
    Optional<Card> findById(Long id);
    List<Card> findByColumnId(Long columnId);
    List<Card> findByBoardId(Long boardId);
    void deleteById(Long id);
    void deleteByColumnId(Long columnId);
} 