package com.taskboard;

import com.taskboard.domain.entities.Board;
import com.taskboard.domain.entities.Card;
import com.taskboard.domain.entities.Column;
import com.taskboard.domain.enums.ColumnType;
import org.junit.Test;
import static org.junit.Assert.*;

public class TaskBoardApplicationTest {

    @Test
    public void testBoardCreation() {
        Board board = new Board("Test Board");
        assertNotNull(board);
        assertEquals("Test Board", board.getName());
        assertTrue(board.getColumns().isEmpty());
    }

    @Test
    public void testColumnCreation() {
        Column column = new Column("Test Column", 1, ColumnType.INITIAL, 1L);
        assertNotNull(column);
        assertEquals("Test Column", column.getName());
        assertEquals(1, column.getOrder());
        assertEquals(ColumnType.INITIAL, column.getType());
    }

    @Test
    public void testCardCreation() {
        Card card = new Card("Test Card", "Test Description");
        assertNotNull(card);
        assertEquals("Test Card", card.getTitle());
        assertEquals("Test Description", card.getDescription());
        assertFalse(card.isBlocked());
    }

    @Test
    public void testBoardStructureValidation() {
        Board board = new Board("Test Board");
        
        Column initialColumn = new Column("A Fazer", 1, ColumnType.INITIAL, board.getId());
        Column pendingColumn = new Column("Em Andamento", 2, ColumnType.PENDING, board.getId());
        Column finalColumn = new Column("Concluído", 3, ColumnType.FINAL, board.getId());
        Column cancellationColumn = new Column("Cancelado", 4, ColumnType.CANCELLATION, board.getId());

        board.addColumn(initialColumn);
        board.addColumn(pendingColumn);
        board.addColumn(finalColumn);
        board.addColumn(cancellationColumn);

        assertTrue(board.isValidStructure());
    }

    @Test
    public void testCardBlocking() {
        Card card = new Card("Test Card", "Test Description");
        assertFalse(card.isBlocked());
        
        card.block("Test reason");
        assertTrue(card.isBlocked());
        assertEquals("Test reason", card.getBlockReason());
        
        card.unblock("Test unblock reason");
        assertFalse(card.isBlocked());
        assertEquals("Test unblock reason", card.getUnblockReason());
    }

    @Test
    public void testColumnTypes() {
        assertEquals("Inicial", ColumnType.INITIAL.getDescription());
        assertEquals("Pendente", ColumnType.PENDING.getDescription());
        assertEquals("Final", ColumnType.FINAL.getDescription());
        assertEquals("Cancelamento", ColumnType.CANCELLATION.getDescription());
    }
} 