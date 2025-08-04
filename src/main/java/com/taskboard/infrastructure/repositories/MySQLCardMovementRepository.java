package com.taskboard.infrastructure.repositories;

import com.taskboard.domain.entities.CardMovement;
import com.taskboard.domain.repositories.CardMovementRepository;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySQLCardMovementRepository implements CardMovementRepository {
    private final Connection connection;

    public MySQLCardMovementRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public CardMovement save(CardMovement movement) {
        String sql = movement.getId() == null 
            ? "INSERT INTO card_movements (card_id, from_column_id, to_column_id, movement_date) VALUES (?, ?, ?, ?)"
            : "UPDATE card_movements SET card_id = ?, from_column_id = ?, to_column_id = ?, movement_date = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, movement.getCardId());
            stmt.setLong(2, movement.getFromColumnId());
            stmt.setLong(3, movement.getToColumnId());
            stmt.setTimestamp(4, Timestamp.valueOf(movement.getMovementDate()));
            
            if (movement.getId() != null) {
                stmt.setLong(5, movement.getId());
            }
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar movimento");
            }
            
            if (movement.getId() == null) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        movement.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Falha ao obter ID gerado");
                    }
                }
            }
            
            return movement;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar movimento", e);
        }
    }

    @Override
    public List<CardMovement> findByCardId(Long cardId) {
        String sql = "SELECT id, card_id, from_column_id, to_column_id, movement_date FROM card_movements WHERE card_id = ? ORDER BY movement_date";
        List<CardMovement> movements = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, cardId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CardMovement movement = mapResultSetToMovement(rs);
                    movements.add(movement);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar movimentos por card ID", e);
        }
        
        return movements;
    }

    @Override
    public List<CardMovement> findByBoardId(Long boardId) {
        String sql = "SELECT cm.id, cm.card_id, cm.from_column_id, cm.to_column_id, cm.movement_date " +
                     "FROM card_movements cm " +
                     "JOIN columns col ON cm.from_column_id = col.id OR cm.to_column_id = col.id " +
                     "WHERE col.board_id = ? " +
                     "ORDER BY cm.movement_date";
        List<CardMovement> movements = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, boardId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CardMovement movement = mapResultSetToMovement(rs);
                    movements.add(movement);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar movimentos por board ID", e);
        }
        
        return movements;
    }

    @Override
    public void deleteByCardId(Long cardId) {
        String sql = "DELETE FROM card_movements WHERE card_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, cardId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar movimentos por card ID", e);
        }
    }

    private CardMovement mapResultSetToMovement(ResultSet rs) throws SQLException {
        CardMovement movement = new CardMovement();
        movement.setId(rs.getLong("id"));
        movement.setCardId(rs.getLong("card_id"));
        movement.setFromColumnId(rs.getLong("from_column_id"));
        movement.setToColumnId(rs.getLong("to_column_id"));
        movement.setMovementDate(rs.getTimestamp("movement_date").toLocalDateTime());
        return movement;
    }
} 