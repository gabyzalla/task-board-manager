package com.taskboard.domain.repositories;

import com.taskboard.domain.entities.Column;
import java.util.List;

public interface ColumnRepository {
    Column save(Column column);
    List<Column> findByBoardId(Long boardId);
    void deleteByBoardId(Long boardId);
    void deleteById(Long id);
} 