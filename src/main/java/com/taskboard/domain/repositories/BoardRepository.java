package com.taskboard.domain.repositories;

import com.taskboard.domain.entities.Board;
import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);
    Optional<Board> findById(Long id);
    List<Board> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    Optional<Board> findByName(String name);
} 